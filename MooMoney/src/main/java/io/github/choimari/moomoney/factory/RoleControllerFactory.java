package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.AdminController;
import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.controller.PremiumController;
import io.github.choimari.moomoney.controller.RegularController;
import io.github.choimari.moomoney.util.InputReader;

public class RoleControllerFactory implements ControllerFactory{

	@Override
	public BaseController createController(InputReader reader, ControllerType type) {
		switch (type) {
        case GUEST: return new GuestController(reader);
        case REGULAR: return new RegularController(reader);
        case PREMIUM: return new PremiumController(reader);
        case ADMIN: return new AdminController(reader);
        default: return null;
    }
	}

}
