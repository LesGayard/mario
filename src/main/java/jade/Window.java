package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width,height;
    private String title;
    private static Window window = null;
    private long gflwWindow;

    
    /*Singleton - one instance */
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "mario";
    }

    /* getters */
    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run(){
        System.out.println("Hello Lwgjl" + Version.getVersion());
        init();
        loop();

        //Free the memory
        glfwFreeCallbacks(gflwWindow);
        glfwDestroyWindow(gflwWindow);
        //terminate
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        /* Set up an error callback */
        GLFWErrorCallback.createPrint(System.err).set();

        /* Initialize GLFW */
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        /* GLFW Configuration */
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);

        /* Create the window */
        gflwWindow = glfwCreateWindow(this.width,this.height,this.title,NULL,NULL);
        if(gflwWindow == NULL){
            throw new IllegalStateException("Failed to create the GLFW Window!");
        }

        /* Mouse Event */
        glfwSetCursorPosCallback(gflwWindow,MouseListener::mousePosCallBack);
        glfwSetMouseButtonCallback(gflwWindow,MouseListener::mouseButtonCallBack);
        glfwSetScrollCallback(gflwWindow,MouseListener::mouseScrollCallBack);

        /* Keyboard Event */
        glfwSetKeyCallback(gflwWindow,KeyListener::keyCallBack);


        /* Make the OpenGL context current */
        glfwMakeContextCurrent(gflwWindow);
        /* Enale v-sync */
        glfwSwapInterval(1);

        /* Make the window visible */
        glfwShowWindow(gflwWindow);

        GL.createCapabilities();

    }
    public void loop(){
        while(!glfwWindowShouldClose(gflwWindow)){
            /* Poll Events = mouse key events */
            glfwPollEvents();

            glClearColor(1.0f,0.0f, 0.0f, 1.0f);
            // how to clear the buffer
            glClear(GL_COLOR_BUFFER_BIT);

            /* Test the Key Event */
            if (KeyListener.keyPressed(GLFW_KEY_SPACE)){
                System.out.println("space key is pressed ");
            }
            glfwSwapBuffers(gflwWindow);
        }

    }
}
