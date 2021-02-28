package Model;

import java.io.Serializable;

public class ModelGameoptionData implements Model, Serializable {

    private String audio;
    private String resolution_x;
    private String resolution_y;
    private String controller;
    private String map;

    public String getAudio() { return audio; }
    public String getController() { return controller; }
    public String getMap() { return map; }
    public String getResolution_x() { return resolution_x; }
    public String getResolution_y() { return resolution_y; }

    public void setAudio(String audio) { this.audio = audio; }
    public void setController(String controller) { this.controller = controller; }
    public void setMap(String map) { this.map = map; }
    public void setResolution_x(String resolution) { this.resolution_x = resolution; }
    public void setResolution_y(String resolution) { this.resolution_y = resolution; }
    public void setAll(String audio,String resolution_x,String resolution_y,String controller,String map)
    {
        setAudio(audio);
        setResolution_y(resolution_y);
        setResolution_x(resolution_x);
        setController(controller);
        setMap(map);
    }
}

