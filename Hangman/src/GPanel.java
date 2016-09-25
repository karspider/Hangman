import javax.swing.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;
import java.util.*;


public class GPanel extends JPanel{
	
	GPanel()
	{
		setPreferredSize(new Dimension(300, 300));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(10));
		g2.setColor(Color.RED);
		
		if(Player.lives <= 10)
		{
			g2.draw(new Line2D.Float(0, 300, 100, 300));
			
			if(Player.lives <= 9)
			{
				g2.draw(new Line2D.Float(50, 300, 50, 0));
				
				if(Player.lives <= 8)
				{
					g2.draw(new Line2D.Float(50, 0, 250, 0));
					
					if(Player.lives <= 7)
					{
						g2.draw(new Line2D.Float(100, 0, 50, 50));
						
						if(Player.lives <= 6)
						{
							g2.draw(new Line2D.Float(200, 0, 200, 50));
							
							if(Player.lives <= 5)
							{
								g2.draw(new Ellipse2D.Float(175, 50, 50, 50));
								
								if(Player.lives <= 4)
								{
									g2.draw(new Line2D.Float(200, 100, 200, 170));
									
									if(Player.lives <= 3)
									{
										g2.draw(new Line2D.Float(200, 170, 230, 250));
										
										if(Player.lives <= 2)
										{
											g2.draw(new Line2D.Float(200, 170, 170, 250));
											
											if(Player.lives <= 1)
											{
												g2.draw(new Line2D.Float(200, 100, 230, 150));
												
												if(Player.lives <= 0)
												{
													g2.draw(new Line2D.Float(200, 100, 170, 150));
												}

											}

										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
