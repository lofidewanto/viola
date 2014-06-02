package com.colinalworth.gwt.viola.web.client.ioc;

import com.colinalworth.gwt.viola.web.client.ioc.Session.SessionProvider;
import com.colinalworth.gwt.viola.web.client.ioc.UserId.UserIdProvider;
import com.colinalworth.gwt.viola.web.client.mvp.ClientPlaceManager;
import com.colinalworth.gwt.viola.web.client.view.CreateProjectViewImpl;
import com.colinalworth.gwt.viola.web.client.view.ExampleViewImpl;
import com.colinalworth.gwt.viola.web.client.view.HomeViewImpl;
import com.colinalworth.gwt.viola.web.client.view.JavaCodeEditorViewImpl;
import com.colinalworth.gwt.viola.web.client.view.ProfileEditorViewImpl;
import com.colinalworth.gwt.viola.web.client.view.ProfileViewImpl;
import com.colinalworth.gwt.viola.web.client.view.ProjectEditorViewImpl;
import com.colinalworth.gwt.viola.web.client.view.SearchProjectViewImpl;
import com.colinalworth.gwt.viola.web.shared.mvp.CreateProjectPresenter.CreateProjectView;
import com.colinalworth.gwt.viola.web.shared.mvp.ExamplePresenter.ExampleView;
import com.colinalworth.gwt.viola.web.shared.mvp.HomePresenter.HomeView;
import com.colinalworth.gwt.viola.web.shared.mvp.JavaCodeEditorPresenter.JavaCodeEditorView;
import com.colinalworth.gwt.viola.web.shared.mvp.PlaceManager;
import com.colinalworth.gwt.viola.web.shared.mvp.PlaceManager.PlaceBasedPresenterFactory;
import com.colinalworth.gwt.viola.web.shared.mvp.PlaceManager.PlaceFactory;
import com.colinalworth.gwt.viola.web.shared.mvp.ProfileEditorPresenter.ProfileEditorView;
import com.colinalworth.gwt.viola.web.shared.mvp.ProfilePresenter.ProfileView;
import com.colinalworth.gwt.viola.web.shared.mvp.ProjectEditorPresenter.ProjectEditorView;
import com.colinalworth.gwt.viola.web.shared.mvp.SearchProjectPresenter.SearchProjectView;
import com.colinalworth.gwt.viola.web.shared.mvp.ViolaPlaceMapper;
import com.colinalworth.gwt.viola.web.shared.mvp.ViolaPlaceMapper.PresenterFactory;
import com.colinalworth.gwt.viola.web.shared.mvp.ViolaPlaces;
import com.colinalworth.gwt.viola.web.shared.request.JobRequest;
import com.colinalworth.gwt.viola.web.shared.request.ProfileRequest;
import com.colinalworth.gwt.viola.web.shared.request.SearchRequest;
import com.colinalworth.gwt.viola.web.shared.request.ViolaRequestQueue;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ViolaClientModule extends AbstractGinModule {
	@Override
	protected void configure() {
		install(new GinFactoryModuleBuilder().build(PresenterFactory.class));

		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

		bind(PlaceFactory.class).to(ViolaPlaces.class);

		bind(ClientPlaceManager.class).in(Singleton.class);
		bind(PlaceManager.class).to(ClientPlaceManager.class).in(Singleton.class);

		bind(PlaceBasedPresenterFactory.class).to(ViolaPlaceMapper.class);

		bind(SearchProjectView.class).to(SearchProjectViewImpl.class);
		bind(ExampleView.class).to(ExampleViewImpl.class);
		bind(CreateProjectView.class).to(CreateProjectViewImpl.class);
		bind(ProjectEditorView.class).to(ProjectEditorViewImpl.class);
		bind(HomeView.class).to(HomeViewImpl.class);

		bind(ProfileView.class).to(ProfileViewImpl.class);
		bind(ProfileEditorView.class).to(ProfileEditorViewImpl.class);

		bind(JavaCodeEditorView.class).to(JavaCodeEditorViewImpl.class);

		bind(String.class).annotatedWith(Session.class).toProvider(SessionProvider.class);
		bind(String.class).annotatedWith(UserId.class).toProvider(UserIdProvider.class);

		bind(ViolaRequestQueue.class).toProvider(QueueProvider.class);
	}

	@Provides
	SearchRequest provideSearchRequest(ViolaRequestQueue queue) {
		return queue.search();
	}
	@Provides
	JobRequest provideJobRequest(ViolaRequestQueue queue) {
		return queue.job();
	}
	@Provides
	ProfileRequest provideProfileRequest(ViolaRequestQueue queue) {
		return queue.profile();
	}

}
