package littleRestServerSide.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import pl.kamsoft.nfz.model.Model;
import pl.kamsoft.nfz.model.SecondModel;

public class ModelGenerator {

	public List<Model> generateModels() {
		List<Model> models = new ArrayList<Model>();
		Random random = new Random();
		List<String> list = Arrays.asList("lol", "test");
		for (int i = 0; i < 10; i++) {
			Model model = new Model();
			model.setString(Integer.toString(i));
			model.setLongNumber(random.nextLong());
			model.setStrings(list);
			model.setSecondModels(generateSecondModels());
			models.add(model);
		}
		return models;
	}

	private List<SecondModel> generateSecondModels() {
		List<SecondModel> secondModels = new ArrayList<SecondModel>();
		for (int i = 0; i < 3; i++) {
			SecondModel model = new SecondModel();
			model.setFirstString("first");
			model.setSecondString("second");
			secondModels.add(model);
		}
		return secondModels;
	}
}
