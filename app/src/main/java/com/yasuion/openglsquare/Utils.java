package com.yasuion.openglsquare;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @Effect 这是一个工具类（色器语言，顶点算法，数据缓冲部分）
 */
public abstract class Utils {

    /**
     * @Effect 顶点坐标;
     */
    public float[] cubePositions = {
            -1.0f, 1.0f, 1.0f,   //正面左上0
            -1.0f, -1.0f, 1.0f,   //正面左下1
            1.0f, -1.0f, 1.0f,    //正面右下2
            1.0f, 1.0f, 1.0f,   //正面右上3
            -1.0f, 1.0f, -1.0f,    //反面左上4
            -1.0f, -1.0f, -1.0f,   //反面左下5
            1.0f, -1.0f, -1.0f,    //反面右下6
            1.0f, 1.0f, -1.0f,     //反面右上7
    };
    /**
     * @Effect 顶点索引（6个面，一个面有六个顶点，也就需要六个索引）;
     */
    public short index[] = {
            6, 7, 4, 6, 4, 5,    //后面
            6, 3, 7, 6, 2, 3,    //右面
            6, 5, 1, 6, 1, 2,    //下面
            0, 3, 2, 0, 2, 1,    //正面
            0, 1, 5, 0, 5, 4,    //左面
            0, 7, 3, 0, 4, 7,    //上面
    };

    /**
     * @Effect 顶点着色(一共8个点，所以需要8个点颜色数据);
     */
    public float color[] = {
//        (1111代表白色）颜色排列：RGB A
            1f, 1f, 1f, 1f,
            0f, 1f, 0f, 1f,
            1f, 1f, 0f, 1f,
            1f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 0.5f, 1f,
            1f, 1f, 1f, 1f,
            0.5f, 0f, 0f, 1f,
    };
    /**
     * @Effect 顶点着色器;
     */
    public final String vertexShaderCode =
            "attribute vec4 vPosition;" +//声明一个用attribute修饰的变量（顶点）
                    "uniform mat4 vMatrix;" +//总变换矩阵
                    "varying  vec4 vColor;" +//颜色易变变量（成对出现）
                    "attribute vec4 aColor;" +//声明一个用attribute修饰的变量（颜色）
                    "void main() {" +
                    "  gl_Position = vMatrix*vPosition;" +//根据总变换的矩阵计算绘制此顶点的位置
                    "  vColor=aColor;" + //将接收的颜色传递给片元着色器
                    "}";
    /**
     * @Effect 片段着色器;
     */
    public final String fragmentShaderCode =
//   片元语言没有默认浮点精度修饰符
//    因此，对于浮点数，浮点数向量和矩阵变量声明，
// 要么声明必须包含一个精度修饰符，要么不默认精度修饰符在之前 已经被声明过。
            "precision mediump float;" +//预定义的全局默认精度
                    "varying vec4 vColor;" +//接收从顶点着色器过来的参数
                    "void main() {" +
                    "  gl_FragColor = vColor;" + //给此片源颜色值
                    "}";

    /**
     * @param shaderType(着色器类型,顶点和片元);
     * @param shaderCode(着色器代码);
     * @return 返回着色器对象
     * @Effect 加载Shader的方法;
     */
    public int loadShader(int shaderType, String shaderCode) {
        //根据type创建顶点着色器或者片元着色器
        int shader = GLES20.glCreateShader(shaderType);
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
////===========================================================

    /**
     * @param ver_Tex(数组,一般为顶点数据);
     * @return Float型缓冲
     * @Effect 获取FloatBuffer数据缓冲;
     */
    public FloatBuffer getFloatBuffer(float ver_Tex[]) {
        //创建顶点坐标数据缓冲
        // vc.length*4是因为一个整数四个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(ver_Tex.length * 4);
        //设置字节顺序
        // 由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer转换
        bb.order(ByteOrder.nativeOrder());
        //转换为Float型缓冲
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        //向缓冲区中放入顶点坐标数据
        vertexBuffer.put(ver_Tex);
        //设置缓冲区起始位置
        vertexBuffer.position(0);
        return vertexBuffer;
    }

    /**
     * @param index(这个是绘制顶点的索引数组);
     * @return Short型缓冲
     * @Effect 获取ShortBuffer数据缓冲;
     */
    public ShortBuffer getShortBuffer(short index[]) {
        ByteBuffer cc = ByteBuffer.allocateDirect(index.length * 2);
        cc.order(ByteOrder.nativeOrder());
        ShortBuffer indexBuffer = cc.asShortBuffer();
        indexBuffer.put(index);
        indexBuffer.position(0);
        return indexBuffer;
    }

}
