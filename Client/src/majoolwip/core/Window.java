package majoolwip.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Window
{
	private JFrame frame;
	private Canvas canvas;
	private BufferedImage image;
	private Graphics g;
	private BufferStrategy bs;
	private CloseWindowListener closeWindowListener;
	
	public Window(GameContainer gc)
	{
		image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		canvas = new Canvas();
		Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setPreferredSize(s);
		
		frame = new JFrame(gc.getTitle());
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame,
						"Are you sure to close this window?", "Really Closing?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					if (closeWindowListener != null){
						System.out.println("EXIT");
						closeWindowListener.onCloseWindow();
					}
					System.exit(0);
				}
			}
		});
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(1);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public void update()
	{
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
	}
	
	public void cleanUp()
	{
		g.dispose();
		bs.dispose();
		image.flush();
		frame.dispose();
	}

	public Canvas getCanvas()
	{
		return canvas;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public void setCloseWindowListener(CloseWindowListener closeWindowListener) {
		this.closeWindowListener = closeWindowListener;
	}

	public interface CloseWindowListener{
		void onCloseWindow();
	}
}
