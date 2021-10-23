package sh.mob.timer.web;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", ""})
public class IndexController {

  private final RoomRepository roomRepository;
  private final String url;

  public IndexController(RoomRepository roomRepository, @Value("${url}") String url) {
    this.roomRepository = roomRepository;
    this.url = url;
  }

  @GetMapping
  public String index(Model model) {
    model.addAttribute("numberOfRooms", roomRepository.count());
    model.addAttribute("numberOfUsers", roomRepository.countUsers());
    model.addAttribute("numberOfConnections", 0);
    model.addAttribute("url", url);
    return "index";
  }

  @PostMapping
  public String post(@ModelAttribute Form form) {
    return String.format("redirect:/%s", form.room());
  }

  public static final class Form {

    private final String room;

    public Form(String room) {
      this.room = room;
    }

    public String room() {
      return room;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj == null || obj.getClass() != this.getClass()) return false;
      var that = (Form) obj;
      return Objects.equals(this.room, that.room);
    }

    @Override
    public int hashCode() {
      return Objects.hash(room);
    }

    @Override
    public String toString() {
      return "Form[" + "room=" + room + ']';
    }
  }
}
