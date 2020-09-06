import java.util.Random;
import java.lang.*;
//importing libraries

public abstract class Block {
    int color;
    int type = 1;
    Axis[] axis = new Axis[4];
    Axis[] temp = new Axis[4];
    Random generator = new Random();
    int randCol = generator.nextInt(7) + 1;
//colour for block generated by generating random number correlating to a specific colour

    public static Block createBlock(Axis point) {
        int index = new Random().nextInt(7) + 1; //random block generated
        Block block = null;
        switch (index) {
            case 1:
                block = new BlockA(point);
                break;
            case 2:
                block = new BlockB(point);
                break;
            case 3:
                block = new BlockZ(point);
                break;
            case 4:
                block = new BlockC(point);
                break;
            case 5:
                block = new BlockT(point);
                break;
            case 6:
                block = new BlockL(point);
                break;
            case 7:
                block = new BlockD(point);
                break;
            default:
                break;
        }
        transAxis(block.getAxis(), block.getTemp());
        return block;
    }
//Different shapes created using squares

    public static void transAxis(Axis[] from, Axis[] to) {
        for (int i = 0; i < from.length; i++) {
            to[i] = new Axis(from[i].x, from[i].y);
        }
    }

    public void rotate() {
        for (int i = 0; i < axis.length; i++) {
            temp[i].x = axis[1].x + (axis[i].y - axis[1].y);
            temp[i].y = axis[1].y - (axis[i].x - axis[1].x);
        }
    }
//rotate block

    public void shiftDown() {
        for (int i = 0; i < axis.length; i++) {
            temp[i] = new Axis(axis[i].x, axis[i].y - 1);
        }
    }
//moves block down

    public void shiftLeft() {
        for (int i = 0; i < axis.length; i++) {
            temp[i] = new Axis(axis[i].x - 1, axis[i].y);
        }
    }
//block moves to the left

    public void shiftRight() {
        for (int i = 0; i < axis.length; i++) {
            temp[i] = new Axis(axis[i].x + 1, axis[i].y);
        }
    }
//block moves to the right

    public Axis[] getAxis() {
        return axis;
    }

    public int getColor() {
        return color;
    }
//gets colour for block

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
//gets type for block and sets it

    public Axis[] getTemp() {
        return temp;
    }


}

class BlockA extends Block {
    protected BlockA(Axis point) {
        color = randCol;
        axis[1] = new Axis(point.x, point.y);
        axis[0] = new Axis(axis[1].x + 1, axis[1].y);
        axis[2] = new Axis(axis[1].x - 1, axis[1].y);
        axis[3] = new Axis(axis[1].x - 1, axis[1].y + 1);
    }
}
class BlockB extends Block {
    protected BlockB(Axis point) {
        color = randCol;
        axis[1] = new Axis(point.x, point.y);
        axis[0] = new Axis(axis[1].x - 1, axis[1].y);
        axis[2] = new Axis(axis[1].x + 1, axis[1].y);
        axis[3] = new Axis(axis[1].x + 1, axis[1].y + 1);
    }
}


class BlockZ extends Block {
    protected BlockZ(Axis point) {
        color = randCol;
        axis[1] = new Axis(point.x, point.y);
        axis[0] = new Axis(axis[1].x + 1, axis[1].y);
        axis[2] = new Axis(axis[1].x, axis[1].y + 1);
        axis[3] = new Axis(axis[1].x - 1, axis[1].y + 1);
    }
}

class BlockC extends Block {
    protected BlockC(Axis point) {
        color = randCol;
        axis[1] = new Axis(point.x, point.y);
        axis[0] = new Axis(axis[1].x - 1, axis[1].y);
        axis[2] = new Axis(axis[1].x, axis[1].y + 1);
        axis[3] = new Axis(axis[1].x + 1, axis[1].y + 1);
    }
}

class BlockT extends Block {
    protected BlockT(Axis point) {
        color = randCol;
        axis[1] = new Axis(point.x, point.y);
        axis[0] = new Axis(axis[1].x - 1, axis[1].y);
        axis[2] = new Axis(axis[1].x + 1, axis[1].y);
        axis[3] = new Axis(axis[1].x, axis[1].y + 1);
    }
}

class BlockL extends Block {
    protected BlockL(Axis point) {
        color = randCol;
        axis[1] = new Axis(point.x, point.y);
        axis[0] = new Axis(axis[1].x - 1, axis[1].y);
        axis[2] = new Axis(axis[1].x + 1, axis[1].y);
        axis[3] = new Axis(axis[1].x + 2, axis[1].y);
    }

    @Override
    public void rotate() {
        // 1
        if (axis[1].x - axis[0].x == 1 && axis[1].y - axis[0].y == 0) {
            temp[1] = new Axis(axis[2].x, axis[2].y);
            temp[0] = new Axis(temp[1].x, temp[1].y + 1);
            temp[2] = new Axis(temp[1].x, temp[1].y - 1);
            temp[3] = new Axis(temp[1].x, temp[1].y - 2);
        }
        // 2
        else if (axis[1].x - axis[0].x == 0 && axis[1].y - axis[0].y == -1) {
            temp[1] = new Axis(axis[2].x, axis[2].y);
            temp[0] = new Axis(temp[1].x + 1, temp[1].y);
            temp[2] = new Axis(temp[1].x - 1, temp[1].y);
            temp[3] = new Axis(temp[1].x - 2, temp[1].y);
        }
        // 3
        else if (axis[1].x - axis[0].x == -1 && axis[0].y - axis[1].y == 0) {
            temp[1] = new Axis(axis[2].x, axis[2].y);
            temp[0] = new Axis(temp[1].x, temp[1].y - 1);
            temp[2] = new Axis(temp[1].x, temp[1].y + 1);
            temp[3] = new Axis(temp[1].x, temp[1].y + 2);
        }
        // 4
        else if (axis[1].x - axis[0].x == 0 && axis[0].y - axis[1].y == -1) {
            temp[1] = new Axis(axis[2].x, axis[2].y);
            temp[0] = new Axis(temp[1].x - 1, temp[1].y);
            temp[2] = new Axis(temp[1].x + 1, temp[1].y);
            temp[3] = new Axis(temp[1].x + 2, temp[1].y);
        }
    }

}

class BlockD extends Block {
    protected BlockD(Axis point) {
        color = randCol;
        axis[1] = new Axis(point.x, point.y);
        axis[0] = new Axis(axis[1].x - 1, axis[1].y);
        axis[2] = new Axis(axis[1].x - 1, axis[1].y + 1);
        axis[3] = new Axis(axis[1].x, axis[1].y + 1);
    }

    @Override
    public void rotate() {
        transAxis(axis, temp);
    }
//Different blocks defined using their x and y points
}
