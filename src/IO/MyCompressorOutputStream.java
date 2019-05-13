package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public  class MyCompressorOutputStream extends OutputStream{
    private List<Byte> temp;
    private OutputStream out;
    private int saver;
    private int count;
    private boolean isBiggerThanMaxByte;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
        this.count = 0;
        this.saver = 0;
        this.isBiggerThanMaxByte = false;
        this.temp = new ArrayList<>();
    }

    public void write(int b){
        try{
        out.write(b);
        }
        catch (IOException e){}
    }

    public void helpToWrite(int b){
        if(b == saver){
            count++;
        }
        else{
            try {
                temp.add((byte)count);
                //out.helpToWrite(count);
                if(isBiggerThanMaxByte)
                {
                    temp.add((byte)-1);
                    // out.helpToWrite(-1);
                    isBiggerThanMaxByte = false;
                }
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
                temp.add(b[index]);
                //out.helpToWrite(b[index]);
            }
            catch (Exception e){
            }
            if(b[index] == -1){
                countM1++;
            }
            index++;
        }

        for (int i = index; i < b.length; i++) {
                 helpToWrite(b[i]);
                if(count == 127)
                {
//                    try {
                        if(!isBiggerThanMaxByte)
                        {
                            isBiggerThanMaxByte = true;
                            temp.add((byte)-1);
                            //   out.helpToWrite(-1);
                        }
                        temp.add((byte)count);
                        //out.helpToWrite(count);
                        count = 0;
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
                }
        }

//        try {
            temp.add((byte)count);
//            out.helpToWrite(count);
            if(isBiggerThanMaxByte)
            {
                temp.add((byte)-1);
//                out.helpToWrite(-1);
                isBiggerThanMaxByte = false;
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        byte[] finalByte = new byte[temp.size()];
        for(int i = 0; i < temp.size(); i++) {
            finalByte[i] = temp.get(i);
        }
        try
        {
            out.write(finalByte);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        temp = new ArrayList<>();
    }

    public OutputStream getOutputStream() {
        return out;
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }
}
