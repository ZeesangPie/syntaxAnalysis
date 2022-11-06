package Entity;

public class Token {
    private String type;
    private int id;
    private String token;

    public Token(String type, int id, String token) {
        this.type = type;
        this.id = id;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type='" + type + '\'' +
                ", id=" + id +
                ", token='" + token + '\'' +
                '}';
    }
}
