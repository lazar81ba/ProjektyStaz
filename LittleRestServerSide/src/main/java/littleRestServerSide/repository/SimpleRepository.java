package littleRestServerSide.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import littleRestServerSide.generator.ModelGenerator;
import pl.kamsoft.nfz.model.Model;

public class SimpleRepository {

	private List<Model> modelList = new ArrayList<Model>();
	private ModelGenerator generator = new ModelGenerator();

	public SimpleRepository() {
		generateModels();
	}

	private void generateModels() {
		modelList.addAll(generator.generateModels());
	}

	public Model getOneModel() {
		return modelList.get(1);
	}

	public List<Model> getModelList() {
		return modelList;
	}
}
