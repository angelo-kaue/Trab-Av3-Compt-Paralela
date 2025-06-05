import org.jocl.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import static org.jocl.CL.*;

public class ContadorParaleloGPU {

    public static int contarPalavra(String caminhoArquivo, String palavraBuscada) throws IOException {
        System.out.println("Executando contagem na GPU...");

        //ler o conteúdo do arquivo
        String conteudo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)), StandardCharsets.UTF_8);
        byte[] textoEmBytes = conteudo.getBytes(StandardCharsets.UTF_8);
        byte[] palavraEmBytes = palavraBuscada.getBytes(StandardCharsets.UTF_8);
        int[] contador = new int[]{0};

        //ativar exceções no JOCL
        setExceptionsEnabled(true);

        //obter plataforma e dispositivo
        cl_platform_id[] plataformas = new cl_platform_id[1];
        clGetPlatformIDs(1, plataformas, null);
        cl_platform_id plataforma = plataformas[0];

        cl_device_id[] dispositivos = new cl_device_id[1];
        clGetDeviceIDs(plataforma, CL_DEVICE_TYPE_GPU, 1, dispositivos, null);
        cl_device_id dispositivo = dispositivos[0];

        //criar contexto e fila
        cl_context contexto = clCreateContext(null, 1, new cl_device_id[]{dispositivo}, null, null, null);
        cl_command_queue fila = clCreateCommandQueueWithProperties(contexto, dispositivo, null, null);

        //buffers
        cl_mem bufferTexto = clCreateBuffer(contexto, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_char * textoEmBytes.length, Pointer.to(textoEmBytes), null);

        cl_mem bufferPalavra = clCreateBuffer(contexto, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_char * palavraEmBytes.length, Pointer.to(palavraEmBytes), null);

        cl_mem bufferContador = clCreateBuffer(contexto, CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_int, Pointer.to(contador), null);

        //carregar kernel
        String codigoKernel = new String(Files.readAllBytes(Paths.get("WordCountKernel.cl")), StandardCharsets.UTF_8);

        cl_program programa = clCreateProgramWithSource(contexto, 1, new String[]{codigoKernel}, null, null);
        clBuildProgram(programa, 0, null, null, null, null);

        cl_kernel kernel = clCreateKernel(programa, "contar_palavra", null);

        //definir argumentos do kernel
        clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(bufferTexto));
        clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(bufferPalavra));
        clSetKernelArg(kernel, 2, Sizeof.cl_int, Pointer.to(new int[]{textoEmBytes.length}));
        clSetKernelArg(kernel, 3, Sizeof.cl_int, Pointer.to(new int[]{palavraEmBytes.length}));
        clSetKernelArg(kernel, 4, Sizeof.cl_mem, Pointer.to(bufferContador));

        //definir tamanho de execução
        long[] tamanhoGlobal = new long[]{textoEmBytes.length};

        //executar kernel
        clEnqueueNDRangeKernel(fila, kernel, 1, null, tamanhoGlobal, null, 0, null, null);

        //ler o resultado
        clEnqueueReadBuffer(fila, bufferContador, CL_TRUE, 0, Sizeof.cl_int, Pointer.to(contador), 0, null, null);

        //limpeza
        clReleaseKernel(kernel);
        clReleaseProgram(programa);
        clReleaseMemObject(bufferTexto);
        clReleaseMemObject(bufferPalavra);
        clReleaseMemObject(bufferContador);
        clReleaseCommandQueue(fila);
        clReleaseContext(contexto);

        return contador[0];
    }
}
