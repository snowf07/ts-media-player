package com.ts.media.player.render.effect;

import android.opengl.GLSurfaceView;

//com.ts.media.player
import  com.ts.media.player.render.view.GSYVideoGLView.ShaderInterface;

import com.ts.media.player.render.view.GSYVideoGLView;


/**
 * Displays the normal video without any effect.
 *
 * @author sheraz.khilji
 */
public class NoEffect implements GSYVideoGLView.ShaderInterface {
    /**
     * Initialize
     */
    public NoEffect() {
    }

    @Override
    public String getShader(GLSurfaceView mGlSurfaceView) {

        String shader = "#extension GL_OES_EGL_image_external : require\n"
                + "precision mediump float;\n"
                + "varying vec2 vTextureCoord;\n"
                + "uniform samplerExternalOES sTexture;\n" + "void main() {\n"
                + "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n"
                + "}\n";

        return shader;

    }
}