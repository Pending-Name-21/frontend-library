package piece;

public abstract class Sound implements IFile {

    private String path;

    public Sound(String path) {
        this.path = path;
    }

    public abstract boolean canPlay();

    @Override
    public String getPath() {
        return this.path;
    }
}
