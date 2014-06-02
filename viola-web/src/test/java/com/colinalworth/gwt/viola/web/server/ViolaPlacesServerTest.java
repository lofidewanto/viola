package com.colinalworth.gwt.viola.web.server;

import com.colinalworth.gwt.viola.web.shared.mvp.CreateProjectPresenter.CreateProjectPlace;
import com.colinalworth.gwt.viola.web.shared.mvp.ProjectEditorPresenter.ProjectEditorPlace;
import com.colinalworth.gwt.viola.web.shared.mvp.ViolaPlaces;
import com.colinalworth.gwt.viola.web.vm.PlaceFactoryModuleBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;


public class ViolaPlacesServerTest {
	@Test
	public void testRouteToString() throws Exception {
		Injector i = Guice.createInjector(new PlaceFactoryModuleBuilder().build(ViolaPlaces.class));
		ViolaPlaces places = i.getInstance(ViolaPlaces.class);


	}

	@Test
	public void testRouteToPlace() throws Exception {
		Injector i = Guice.createInjector(new PlaceFactoryModuleBuilder().build(ViolaPlaces.class));
		ViolaPlaces places = i.getInstance(ViolaPlaces.class);

		CreateProjectPlace createProjectPlace = (CreateProjectPlace) places.route("proj/new");

		ProjectEditorPlace projectEditor = (ProjectEditorPlace) places.route("proj/1234abcd");
		assert projectEditor.getId().equals("1234abcd") : projectEditor.getId();
		assert projectEditor.getActiveFile() == null || "".equals(projectEditor.getActiveFile()) : projectEditor.getActiveFile();

		projectEditor = (ProjectEditorPlace) places.route("proj/1234abcd/");
		assert projectEditor.getId().equals("1234abcd") : projectEditor.getId();
		assert projectEditor.getActiveFile() == null || "".equals(projectEditor.getActiveFile()) : projectEditor.getActiveFile();

		projectEditor = (ProjectEditorPlace) places.route("proj/1234abcd/foo/bar/File.java");
		assert projectEditor.getId().equals("1234abcd") : projectEditor.getId();
		assert projectEditor.getActiveFile().equals("foo/bar/File.java") : projectEditor.getActiveFile();
	}
}
