package edu.iis.mto.statickmock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import edu.iis.mto.staticmock.Configuration;
import edu.iis.mto.staticmock.ConfigurationLoader;
import edu.iis.mto.staticmock.IncomingInfo;
import edu.iis.mto.staticmock.IncomingNews;
import edu.iis.mto.staticmock.NewsLoader;
import edu.iis.mto.staticmock.NewsReaderFactory;
import edu.iis.mto.staticmock.PublishableNews;
import edu.iis.mto.staticmock.SubsciptionType;
import edu.iis.mto.staticmock.reader.NewsReader;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest( SingletonService.class )
public class NewsLoaderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		mockStatic(ConfigurationLoader.class);
		mockStatic(NewsReaderFactory.class);
		
		NewsLoader newsLoader = new NewsLoader();
		IncomingNews incomingNews = new IncomingNews();
		incomingNews.add(mockIncomingInfo(SubsciptionType.A));
		incomingNews.add(mockIncomingInfo(SubsciptionType.A));
		incomingNews.add(mockIncomingInfo(SubsciptionType.B));
		incomingNews.add(mockIncomingInfo(SubsciptionType.C));

		
		ConfigurationLoader configurationLoader = mock(ConfigurationLoader.class);
		NewsReaderFactory newsReaderFactory = mock(NewsReaderFactory.class);
		Configuration config = mock(Configuration.class);
		NewsReader reader = mock(NewsReader.class);

		
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
		when(ConfigurationLoader.getInstance().loadConfiguration()).thenReturn(config);
		
		when(NewsReaderFactory.getReader(config.getReaderType())).thenReturn(reader);
		when(reader.read()).thenReturn(incomingNews);
		
		PublishableNews publishableNews = newsLoader.loadNews();
	}

	private IncomingInfo mockIncomingInfo(SubsciptionType type){
		
		IncomingInfo incomingInfo = mock(IncomingInfo.class);
		when(incomingInfo.getSubscriptionType()).thenReturn(type);
		
		return incomingInfo;
	}
}
