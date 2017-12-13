package com.yasuion.openglsquare;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Create by AcooL 2017/12/13
 */

public class Cube extends Utils {

    private FloatBuffer verBuffer, colorBuffer;
    private ShortBuffer indexBuffer;
    private int mProgram;
    private int vPosition;
    private int aColor;
    private int vMatrix;

    //索引法绘制
    public Cube() {
        initData();
        initShader();
    }


    public void initData() {
        //顶点
        verBuffer = getFloatBuffer(cubePositions);
        //颜色
        colorBuffer = getFloatBuffer(color);
        //索引
        indexBuffer = getShortBuffer(index);
    }

    private void initShader() {
        //创建程序
        mProgram = GLES20.glCreateProgram();
        //关联着色器源码
        GLES20.glAttachShader(mProgram,//着色程序
                loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode));//加载 顶点着色器 源码
        GLES20.glAttachShader(mProgram,//着色程序
                loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode));//加载 片元着色器 源码
        //连接到程序
        GLES20.glLinkProgram(mProgram);
        //获取GLSL的变量id
        vPosition = GLES20.glGetAttribLocation(mProgram, //从这着色程序的源码里面获取GLSL的变量id
                "vPosition");//顶点着色器对应的变量名字
        aColor = GLES20.glGetAttribLocation(mProgram,
                "aColor");//顶点着色器对应的变量名字
        vMatrix = GLES20.glGetUniformLocation(mProgram,
                "vMatrix");//顶点着色器对应的变量名字
    }

    public void drawSelf() {
        //将程序加到OpenGLES环境中
        GLES20.glUseProgram(mProgram);
        //变换矩阵
        //初始化变换矩阵
        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);
        //位移 z轴正向位移
        Matrix.translateM(mMMatrix, 0, 0, 0, 1);
        //旋转
        Matrix.rotateM(mMMatrix, 0, xAngle, 1, 1, 1);
        //给GLSL变量赋值
        GLES20.glUniformMatrix4fv(vMatrix, 1, false, getMatrix(mMMatrix), 0);
        //设置数据
        GLES20.glVertexAttribPointer(vPosition,
                3, GLES20.GL_FLOAT,
                false, 0, verBuffer);
        GLES20.glVertexAttribPointer(aColor, 4,
                GLES20.GL_FLOAT, false, 0, colorBuffer);
        //开启 顶点和颜色
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glEnableVertexAttribArray(aColor);
        //绘制
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
        GLES20.glDisableVertexAttribArray(vPosition);
        GLES20.glDisableVertexAttribArray(aColor);
    }


    // 设置矩阵变换的所用到的矩阵
    public static float[] mProjMatrix = new float[16];//4x4矩阵 投影用
    public static float[] mVMatrix = new float[16];//摄像机位置朝向9参数矩阵
    public static float[] mMVPMatrix;//最后起作用的总变换矩阵
    public static float[] mMMatrix = new float[16];//具体物体的移动旋转矩阵，旋转、平移
    public float xAngle = 0;//绕x轴旋转的角度

    //矩阵相乘操作
    public float[] getMatrix(float[] spec) {
        mMVPMatrix = new float[16];
        ////矩阵相乘操作()
        /*
         *  矩阵乘法计算, 将两个矩阵相乘, 并存入到第三个矩阵中
		 *  六个参数 :
		 *  ①② 参数 : 结果矩阵, 结果矩阵起始位移
		 *  ③④ 参数 : 左矩阵, 结果矩阵起始位移
		 *  ⑤⑥ 参数 : 右矩阵, 结果矩阵起始位移
		 */
        //位移操作
        Matrix.multiplyMM(mMVPMatrix, 0
                , mVMatrix, 0,
                spec, 0);

        //合并投影和视口矩阵
        Matrix.multiplyMM(mMVPMatrix, 0
                , mProjMatrix, 0,
                mMVPMatrix, 0);

        return mMVPMatrix;

    }
}
