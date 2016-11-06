package majoolwip.core.components;

import majoolwip.core.GameContainer;
import majoolwip.core.Renderer;

import java.util.ArrayList;
import java.util.Iterator;

/*
update : add function remove Object by tag
author : hungtd
 */
public class ObjectManager
{
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	public void updateObjects(GameContainer gc, float dt)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update(gc, dt);
			
			if(objects.get(i).isDead())
				objects.remove(i);
		}
	}

	public void removeObject(String tag){
		for(Iterator<GameObject> t = objects.iterator(); t.hasNext();)
		{
			GameObject gameObject = t.next();
			if (gameObject.getTag().equals(tag)){
				t.remove();
				break;
			}
		}
	}

	public void renderObjects(GameContainer gc, Renderer r)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).render(gc, r);
		}
	}
	
	public void diposeObjects()
	{
		for(GameObject go : objects)
		{
			go.dispose();
		}
	}
	
	public void addObject(GameObject object)
	{
		objects.add(object);
	}
	
	public GameObject findObject(String tag)
	{
		for(GameObject go : objects)
		{
			if(go.getTag().equalsIgnoreCase(tag))
			{
				return go;
			}
		}
		
		return null;
	}
	
	
}
