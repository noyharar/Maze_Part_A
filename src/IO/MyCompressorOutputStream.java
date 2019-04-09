package IO;

import java.io.OutputStream;

public  class MyCompressorOutputStream extends OutputStream{

    private OutputStream out;
    private int saver;
    private int count;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
        this.count = 0;
        this.saver = 0;
    }

    public void write(int b){
        if(b == saver){
            count++;
        }
        else{
            try {
                out.write(count);
                count = 1;
                saver = b;
            }
            catch (Exception e){

            }
        }
    }

    public void write(byte[] b){
        int countM1 = 0;
        int index = 0;

        while(countM1 < 6){
            try {
                out.write(b[index]);
            }
            catch (Exception e){
            }
            if(b[index] == -1){
                countM1++;
            }
            index++;
        }

        for (int i = index; i < b.length; i++) {
                write(b[i]);
        }
    }

    public OutputStream getOutputStream() {
        return out;
    }


}
