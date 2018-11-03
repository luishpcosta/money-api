package br.com.lhpc.money.api.event.listenner;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lhpc.money.api.event.ResourceCreatedEvent;

@Component
public class ResourceCreatedListenner implements ApplicationListener<ResourceCreatedEvent> {

	@Override
	public void onApplicationEvent(ResourceCreatedEvent event) {
		event.getResponse().setHeader("Location", ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(event.getId()).toUri().toASCIIString());
	}

}
