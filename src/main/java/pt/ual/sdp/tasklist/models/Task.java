package pt.ual.sdp.tasklist.models;

public class Task {
    private String description;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            return this.description.compareToIgnoreCase(((Task) obj).getDescription()) == 0;
        } else {
            return super.equals(obj);
        }
    }

    private String toJSON() {
        return "{\"description\":\"" + this.description + "\"}";
    }

    @Override
    public String toString() {
        return this.toJSON();
    }
}
