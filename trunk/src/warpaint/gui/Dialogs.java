/*
 * Created on 01.10.2004
 * Copyright (c) 2004 by Christian Dietrich, Boris Leidner, 
 * Jan Gall and Sammy Okasha
 *
 * This file is part of warpainting.
 *
 * warpainting is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * warpainting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with warpainting; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 * Provides standard message dialogs for error and information messages.
 */
 
package warpaint.gui;

import javax.swing.*;

/**
 * @author chris
 *
 * Provides standard message dialogs for error and information messages, mostly wrappers around JOptionPane methods.
 */
public class Dialogs {
	
	/**
	 * Displays an error message with a given title and message. 
	 * @param msg
	 * @param title
	 */
	public static void errorMsg(final String msg, final String title) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				JOptionPane.showMessageDialog(null, "An error occurred:\n\n "+msg, title, JOptionPane.ERROR_MESSAGE);
			}
        });
	}
	
	/**
	 * Displays a given error message. 
	 * @param msg
	 */
	public static void errorMsg(final String msg) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				JOptionPane.showMessageDialog(null, "An error occurred:\n\n "+msg, "Error", JOptionPane.ERROR_MESSAGE);
			}
        });
	}
	
}
