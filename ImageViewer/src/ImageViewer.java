import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class ImageViewer {
	public static void main(String[] args) {
		JFrame jFrame = new ImageViewerFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}
}

class ImageViewerFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3095454353378177587L;
	private JLabel jLabel;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 400;
	private JFileChooser jFileChooser;
	
	public ImageViewerFrame() {
		setTitle("ImageViewer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		jLabel = new JLabel();
		add(jLabel);
		
		jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new File("."));
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenuItem openItem = new JMenuItem("Open");
		menu.add(openItem);
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = jFileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					String name = jFileChooser.getSelectedFile().getPath();
					jLabel.setIcon(new ImageIcon(name));
				}
			}
			
		});
		
		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);	
			}
			
		});
	}
}