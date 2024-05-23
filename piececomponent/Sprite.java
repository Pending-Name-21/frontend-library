package piececomponent;

public class Sprite implements IFile {

    private Coord position;
    private Size size;
    private String path;

    public Sprite(Coord position, Size size, String path) {
        this.position = position;
        this.size = size;
        this.path = path;
    }

    public Coord getPosition() {
        return position;
    }

    public void setPosition(Coord position) {
        this.position = position;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String getPath() {
        return this.path;
    }
    
}
