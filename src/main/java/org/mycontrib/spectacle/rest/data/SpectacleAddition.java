package org.mycontrib.spectacle.rest.data;

import org.mycontrib.spectacle.entity.Spectacle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SpectacleAddition {
	Spectacle spectacle;
	Long categoryId;
}
