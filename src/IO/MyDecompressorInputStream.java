package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private  InputStream in;
    private boolean isBiggerThanMaxByte;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
        isBiggerThanMaxByte = false;
    }


    @Override
    public int read() throws IOException {
        return in.read();
    }

    public int read(byte[] b) throws IOException {
        byte[] copyByte = new byte[b.length];
        int countTwoZeros = 0;
        int read = in.read(copyByte);
            /*while(read != -1){
                read = in.read(copyByte)
            }
             */
        int countM1 = 0;
        int index = 0;

        while (countM1 < 6)
        {
            b[index] = copyByte[index];
            if (b[index] == -1)
            {
                countM1++;
            }
            index++;
        }

        byte num = 0;
        int inForIndex = index;
        for (int i = index; i < b.length; ) {
            // Iterates over every
            for (int j = 0; j < copyByte[inForIndex]; j++) {
                b[i] = num;
                i++;
                countTwoZeros = 0;
            }
            if(copyByte[inForIndex] == -1)
            {
                isBiggerThanMaxByte = !isBiggerThanMaxByte;
            }
            else if(copyByte[inForIndex] == 0)
            {
                countTwoZeros++;
            }
            if(!isBiggerThanMaxByte)
            {
                if (num == 0) {
                    num = 1;
                } else {
                    num = 0;
                }
            }
            if(countTwoZeros > 2)
                break;
            inForIndex++;
        }
        return read;
    }


}
