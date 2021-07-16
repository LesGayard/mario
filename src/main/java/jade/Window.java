package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

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

    /*TO DO : Recréer des getters */
    public float r,g,b,a;
    private boolean fadeToBlack = false;

    private static Scene currentScene;
    
    /*Singleton - one instance */
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "mario";
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;
    }



    public static void changeScene (int newScene){
        switch (newScene){
            case 0:
                currentScene = new LevelEditorScene();
                //currentScene.init();
                break;
            case 1:
                currentScene = new LevelEditorScene();
                break;
            default:
                assert false: "Unknown scene " + newScene;
                break;
        }
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

        Window.changeScene(0);

    }
    public void loop(){
        /* Time */
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while(!glfwWindowShouldClose(gflwWindow)){
            /* Poll Events = mouse key events */
            glfwPollEvents();

            glClearColor(r,g,b,a);
            // how to clear the buffer
            glClear(GL_COLOR_BUFFER_BIT);

            if (fadeToBlack) {
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
            }

            if (KeyListener.keyPressed(GLFW_KEY_0)) {
                fadeToBlack = true;

            if(dt >= 0.0) {
                currentScene.update(dt);
            }}
            glfwSwapBuffers(gflwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

    }
}
