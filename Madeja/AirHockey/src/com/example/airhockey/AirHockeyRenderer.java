/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/
package com.example.airhockey;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.airhockey.android.util.LoggerConfig;
import com.airhockey.android.util.ShaderHelper;
import com.airhockey.android.util.TextResourceReader;
import com.example.airhockey.R;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import static android.opengl.GLES20.*;


public class AirHockeyRenderer implements Renderer {
	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;
	private static final String U_COLOR = "u_Color";
	private int uColorLocation;
	private int program;
	private final Context context;
	private static final int POSITION_COMPONENT_COUNT =2;
	private static final int BYTES_PER_FLOAT = 4;
	private final FloatBuffer vertexData;
	
	
	public AirHockeyRenderer(Context context){
		this.context = context;
		float[] tableVerticesWithTriangles = {		
			
			//Lines
				//center
				0.01f, 0.21f,
				0.01f, -0.40f,
				//1st
				-0.22f, 0.26f,
				-0.22f, -0.35f,
				//up
				-0.22f, 0.26f,
				0.42f, 0.41f,
				
				
				//2nd
				-0.44f, 0.31f,
				-0.44f, -0.30f,
				//up
				-0.44f, 0.31f,
				0.24f, 0.45f,
				
				//3rd
				-0.66f, 0.36f,
				-0.66f, -0.25f,
				
				//pahigda
				//
				0.01f, 0.21f,
				-0.66f, 0.36f,
				
				
				//
				0.01f, -0.40f,
				-0.66f, -0.25f,
				
				//face up
				//1st outside
				-0.66f, 0.36f,
				0.01f, 0.50f,
				//2nd outside
				0.66f, 0.36f,
				0.01f, 0.50f,
				
				
				
				
				//3rd outside
				0.01f, 0.21f,
				0.66f, 0.36f,
				//down 1
				0.01f, -0.21f,
				0.66f, -0.06f,
				//down 2
				0.01f, 0.0f,
				0.66f, 0.15f,
				
				//right face
				//1st
				0.66f, 0.36f,
				0.66f, -0.25f,
				//2nd
				-0.01f, -0.40f,
				0.66f, -0.25f,
				
								/////1st line squares from the left
											//1st square
											0.01f, 0.0f,
											0.01f, 0.21f,
											-0.22f, 0.05f,
										
											-0.22f, 0.26f,
											0.01f, 0.21f,
											-0.22f, 0.05f,
											
											//2nd square
											-0.22f, 0.26f,
											-0.44f, 0.10f, 
											-0.22f, 0.05f,
											
											-0.44f, 0.31f,
											-0.22f, 0.26f,
											-0.44f, 0.10f, 
											
											//3rd square
											-0.44f, 0.31f,
											-0.66f, 0.15f, 
											-0.44f, 0.10f, 
											
											-0.44f, 0.31f,
											-0.66f, 0.15f, 
											-0.66f, 0.36f,
											
											
											
											////2nd line squares
											//1st square
											0.01f, 0.0f,
											0.01f, -0.21f,
											-0.22f, -0.15f,
											
											0.01f, 0.0f,
											-0.22f, 0.05f,
											-0.22f, -0.15f,
											
											//2nd square
											-0.44f, -0.10f,
											-0.22f, 0.05f,
											-0.22f, -0.15f,
											
											-0.44f, -0.10f,
											-0.22f, 0.05f,
											-0.44f, 0.10f,
											
											//3rd
											-0.44f, -0.10f,
											-0.66f, -0.05f,
											-0.44f, 0.10f,
											
											-0.66f, 0.15f, 
											-0.66f, -0.05f,
											-0.44f, 0.10f,
											
											
											///3rd line squares
											//1st square 
											0.01f, -0.40f,
											0.01f, -0.21f,
											-0.22f, -0.15f,
											
											-0.22f, -0.35f,
											0.01f, -0.40f,
											-0.22f, -0.15f,
											
											//2nd square
											-0.22f, -0.35f,
											-0.44f, -0.10f,
											-0.22f, -0.15f,
											
											-0.22f, -0.35f,
											-0.44f, -0.10f,
											-0.44f, -0.30f,
											
											//3rd square
											-0.66f, -0.05f,
											-0.44f, -0.10f,
											-0.44f, -0.30f,
											
											-0.66f, -0.05f,
											-0.66f, -0.25f,
											-0.44f, -0.30f,
											
				//1st line squares in right
											//1st square
											0.01f, 0.0f,
											0.01f, 0.21f,
											0.22f, 0.05f,
				
											0.22f, 0.26f,
											0.01f, 0.21f,
											0.22f, 0.05f,
											
											//2nd square
											0.22f, 0.26f,
											0.48f, 0.32f,
											0.22f, 0.05f,
											
											0.48f, 0.11f,
											0.48f, 0.32f,
											0.22f, 0.05f,
											
											//3rd square
											0.48f, 0.11f,
											0.48f, 0.32f,
											0.66f, 0.15f,
											
											0.66f, 0.36f,
											0.48f, 0.32f,
											0.66f, 0.15f,
											
						//2nd line squares 
											//1st square
											0.01f, 0.0f,
											0.01f, -0.21f,
											0.22f, 0.05f,
											
											0.22f, -0.16f,
											0.01f, -0.21f,
											0.22f, 0.05f,
											
											//2nd square
											0.22f, -0.16f,
											0.48f, -0.10f,
											0.22f, 0.05f,
											
											0.22f, 0.05f,
											0.48f, -0.10f,
											0.48f, 0.11f,
		
											//3rd square
											0.66f, 0.15f,
											0.48f, -0.10f,
											0.48f, 0.11f,
		
											0.66f, 0.15f,
											0.48f, -0.10f,
											0.66f, -0.06f,
		//3rd line
											//1st square
											0.01f, -0.40f,
											0.01f, -0.21f,
											0.22f, -0.16f,
											
											0.01f, -0.40f,
											0.22f, -0.35f,
											0.22f, -0.16f,
											
											//2nd square
											0.48f, -0.10f,
											0.22f, -0.35f,
											0.22f, -0.16f,
		
											0.48f, -0.10f,
											0.22f, -0.35f,
											0.48f, -0.29f,
											
											//3rd square
											0.48f, -0.10f,
											0.66f, -0.06f,
											0.48f, -0.29f,
		
											0.66f, -0.25f,
											0.66f, -0.06f,
											0.48f, -0.29f,
		
											
					//3rd face of the cube top squares
											
											//1st squares
											-0.22f, 0.26f,
											0.01f, 0.21f,
											0.01f, 0.31f,
		
											0.22f, 0.26f,
											0.01f, 0.21f,
											0.01f, 0.31f,
											
											//2nd squares
											0.22f, 0.26f,
											0.22f, 0.36f,
											0.01f, 0.31f,
											
											0.22f, 0.26f,
											0.22f, 0.36f,
											0.48f, 0.32f,
											
											//3rd square
											0.42f, 0.41f,
											0.22f, 0.36f,
											0.48f, 0.32f,
											
											0.42f, 0.41f,
											0.66f, 0.36f,
											0.48f, 0.32f,
								
											
								//2nd lines squares
											//1st
											-0.22f, 0.26f,
											-0.44f, 0.31f,
											0.01f, 0.31f,
											
											-0.17f, 0.37f,
											-0.44f, 0.31f,
											0.01f, 0.31f,
											
											//2nd
											-0.17f, 0.37f,
											0.22f, 0.36f,
											0.01f, 0.31f,
											
											-0.17f, 0.37f,
											0.22f, 0.36f,
											0.07f, 0.42f,
											
											//3rd 
											0.42f, 0.41f,
											0.22f, 0.36f,
											0.07f, 0.42f,
											
											0.42f, 0.41f,
											0.24f, 0.45f,
											0.07f, 0.42f,
		
							//3rd lines square
											//1st square
											-0.17f, 0.37f,
											-0.44f, 0.31f,
											-0.66f, 0.36f,
											
											-0.17f, 0.37f,
											-0.36f, 0.42f,
											-0.66f, 0.36f,
											
											//2nd square
											-0.17f, 0.37f,
											-0.36f, 0.42f,
											-0.09f, 0.48f,
											
											-0.17f, 0.37f,
											0.07f, 0.42f,
											-0.09f, 0.48f,
											
											//3rd square
											0.24f, 0.45f,
											0.07f, 0.42f,
											-0.09f, 0.48f,
											
											0.24f, 0.45f,
											0.01f, 0.50f,
											-0.09f, 0.48f,
											
											
		};
		vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexData.put(tableVerticesWithTriangles);
	}
	
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to red. The first component is
        // red, the second is green, the third is blue, and the last
        // component is alpha, which we don't use in this lesson.
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        
        int vertexShaderId = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        
        program = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId);
        
        if(LoggerConfig.ON){
        	ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);        
    }

	/**
     * onSurfaceChanged is called whenever the surface has changed. This is
     * called at least once when the surface is initialized. Keep in mind that
     * Android normally restarts an Activity on rotation, and in that case, the
     * renderer will be destroyed and a new one created.
     * 
     * @param width
     *            The new width, in pixels.
     * @param height
     *            The new height, in pixels.
     */
    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);
    }

    /**
     * OnDrawFrame is called whenever a new frame needs to be drawn. Normally,
     * this is done at the refresh rate of the screen.
     */
    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface
        glClear(GL_COLOR_BUFFER_BIT);
        
        glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 0, 30);
        
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 30, 6);
        
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 36, 6);
        
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 42, 6);
                
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 48, 6);
        
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 54, 6);
        
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 60, 6);
        
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 66, 6);
        
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 72, 6);
        
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 78, 6);
    
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 84, 6);
    
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 90, 6);
    
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 96, 6);
        
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 102, 6);
        
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 108, 6);
        
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 114, 6);
    
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 120, 6);
        
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 126, 6);
        
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 132, 6);
        
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 138, 6);
    
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 144, 6);
        
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 150, 6);
        
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 156, 6);
    
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 162, 6);
        
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 168, 6);
    
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 174, 6);
        
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 180, 6);
        
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 186, 6);
    }
}
