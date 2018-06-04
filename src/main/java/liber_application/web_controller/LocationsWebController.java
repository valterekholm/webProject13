package liber_application.web_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.LocationRepository;
import liber_application.model.Book;
import liber_application.model.Location;

@Controller
@RequestMapping("locations")
public class LocationsWebController {

	@Autowired
	LocationRepository locationsRepo;
	
	@GetMapping("/all")
	public String getAllLocations(Model m) {
		m.addAttribute("locations", locationsRepo.findAll());
		return "listlocations";
	}
	
	@GetMapping("/addLocation")
	public String addLocation(Model m) {
		m.addAttribute("location", new Location());
		return "addlocation";
	}
	
	@PostMapping("/addLocation")
	public String saveLocation(Location location, Model m) {
		m.addAttribute("location", new Location());
		if(location.getDescription().isEmpty()) {
			location.setDescription(null);
		}
		Location savedL = locationsRepo.save(location);
		m.addAttribute("message", "Saved location: " + savedL.getName());
		return "addlocation";
	}
}