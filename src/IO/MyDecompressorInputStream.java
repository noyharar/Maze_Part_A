package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private  InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] b) throws IOException {
        byte[] copyByte = new byte[b.length];
        in.read(copyByte);

        int countM1 = 0;
        int index = 0;

        while(countM1 < 6)
        {
            b[index] = copyByte[index];
            if(b[index] == -1){
                countM1++;
            }
            index++;
        }
        byte num = 0;
        int inForIndex = index;
        for (int i = index; i < b.length; ) {
            for (int j = 0; j < copyByte[inForIndex]; j++) {
                b[i] = num;
                i++;
            }
            if(num == 0)
            {
                num = 1;
            }
            else
            {
                num = 0;
            }
            inForIndex++;
        }

        return -1;
    }


}
