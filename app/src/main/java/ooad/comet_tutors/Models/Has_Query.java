package ooad.comet_tutors.Models;

/**
 * Created by Dillon on 10/20/2014.
 */
public class Has_Query {
    String id;
    String email;
    String query;

    public Has_Query() {
    }

    public Has_Query(String id, String email, String query) {
        this.id = id;
        this.email = email;
        this.query = query;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
