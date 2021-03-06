/*
 *   This file is part of ContractManager for Jameica.
 *   Copyright (C) 2010-2011  Jan Rieke
 *
 *   ContractManager is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   ContractManager is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.janrieke.contractmanager.gui.action;

import de.janrieke.contractmanager.Settings;
import de.janrieke.contractmanager.gui.control.ContractControl;
import de.janrieke.contractmanager.rmi.Costs;
import de.willuhn.jameica.gui.Action;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Action for "delete contract".
 */
public class DeleteCostEntry implements Action {

	private ContractControl contract;

	public DeleteCostEntry(ContractControl contract) {
		this.contract = contract;
	}

	/**
	 * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
	 */
	public void handleAction(Object context) throws ApplicationException {

		// check if the context is a contract
		if (context == null || !(context instanceof Costs))
			throw new ApplicationException(Settings.i18n().tr(
					"Please choose a cost entry."));

		Costs c = (Costs) context;

		try {
			//YesNoDialog d = new YesNoDialog(YesNoDialog.POSITION_CENTER);
			//d.setTitle(Settings.i18n().tr("Are you sure?"));
			//d.setText(Settings.i18n().tr(
			//		"Do you really want to delete these cost entry?"));

			//Boolean choice = (Boolean) d.open();
			//if (!choice.booleanValue())
			//	return;

			contract.removeCostEntry(c);
			//c.delete();
			//GUI.getStatusBar().setSuccessText(
			//		Settings.i18n().tr("Cost entry deleted successfully"));
		} catch (Exception e) {
			Logger.error("error while deleting costs", e);
			throw new ApplicationException(Settings.i18n().tr(
					"Error while deleting cost entry"));
		}
	}
}