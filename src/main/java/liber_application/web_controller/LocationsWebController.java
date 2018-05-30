package liber_application.web_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.LocationRepository;

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
}