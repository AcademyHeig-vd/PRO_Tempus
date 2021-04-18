package ch.heigvd.pro.calendarview.Controlers;

public class Rappel {
    final int id;
    String content;

    public Rappel(int id, String content){
        this.content = content;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

   

}
