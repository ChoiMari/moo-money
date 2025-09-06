package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.views.View;

public abstract class ViewAbstractFactory {
	abstract View createView(String viewType);
}
