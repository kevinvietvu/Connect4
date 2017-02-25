import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;

public class MyButton extends JButton 
{
	private Color color = Color.WHITE;
	
	public MyButton()
	{
		super();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D ga = (Graphics2D) g;
		ga.setPaint(this.color);
		ga.fillOval(getHorizontalAlignment(), getVerticalAlignment(), getWidth()-10, getHeight()-10);
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public void setBackground(Color c)
	{
		super.setBackground(c);
		
	}
	
	public void setColor(Color c)
	{
		this.color = c;
		this.repaint();
	}
	
}