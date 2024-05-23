package piece;

public abstract class Sound implements IFile {

    private String path;

    public boolean canPlay() {
        return false;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return this.path;
    }
}
