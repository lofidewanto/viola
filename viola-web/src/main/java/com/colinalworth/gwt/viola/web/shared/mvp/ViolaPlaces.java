package com.colinalworth.gwt.viola.web.shared.mvp;

import com.colinalworth.gwt.places.shared.PlaceManager.PlaceFactory;
import com.colinalworth.gwt.viola.web.shared.mvp.CreateProjectPresenter.CreateProjectPlace;
import com.colinalworth.gwt.viola.web.shared.mvp.HomePresenter.HomePlace;
import com.colinalworth.gwt.viola.web.shared.mvp.ProfileEditorPresenter.ProfileEditorPlace;
import com.colinalworth.gwt.viola.web.shared.mvp.ProfilePresenter.ProfilePlace;
import com.colinalworth.gwt.viola.web.shared.mvp.ProjectEditorPresenter.ProjectEditorPlace;
import com.colinalworth.gwt.viola.web.shared.mvp.SearchProfilePresenter.SearchProfilePlace;
import com.colinalworth.gwt.viola.web.shared.mvp.SearchProjectPresenter.SearchProjectPlace;

public interface ViolaPlaces extends PlaceFactory {

	@Route(path = "search/project/?q={query}", priority = 1)
	SearchProjectPlace searchProject();

	@Route(path = "proj/new?from={copy?}", priority = 1)
	CreateProjectPlace createProject();

	@Route(path = "proj/{id}/{activeFile?}?code={code?}", priority = 2)
	ProjectEditorPlace editProject();

	@Route(path = "profile/{id}/edit/", priority = 1)
	ProfileEditorPlace editProfile();

	@Route(path = "profile/{id}/", priority = 2)
	ProfilePlace viewProfile();

	@Route(path = "search/profile/?q={query}", priority = 1)
	SearchProfilePlace searchProfile();

	@Route(path = "", priority = 10)
	HomePlace home();
}
