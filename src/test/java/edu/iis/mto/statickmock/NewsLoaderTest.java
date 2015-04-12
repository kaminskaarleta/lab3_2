package edu.iis.mto.statickmock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest( { ConfigurationLoader.class, NewsReaderFactory.class } )

public class NewsLoaderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void stateTest_validateSegregationTypesOfNews() {
		mockStatic(ConfigurationLoader.class);
		mockStatic(NewsReaderFactory.class);
		
		NewsLoader newsLoader = new NewsLoader();
		IncomingNews incomingNews = new IncomingNews();
		incomingNews.add(mockIncomingInfo(SubsciptionType.A));
		incomingNews.add(mockIncomingInfo(SubsciptionType.A));
		incomingNews.add(mockIncomingInfo(SubsciptionType.B));
		incomingNews.add(mockIncomingInfo(SubsciptionType.NONE));

		
		ConfigurationLoader configurationLoader = mock(ConfigurationLoader.class);
		NewsReaderFactory newsReaderFactory = mock(NewsReaderFactory.class);
		Configuration config = mock(Configuration.class);
		NewsReader reader = mock(NewsReader.class);

		
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
		when(ConfigurationLoader.getInstance().loadConfiguration()).thenReturn(config);
		
		when(NewsReaderFactory.getReader(Mockito.anyString())).thenReturn(reader);
		when(reader.read()).thenReturn(incomingNews);
		
		PublishableNews publishableNews = newsLoader.loadNews();
	}

	private IncomingInfo mockIncomingInfo(SubsciptionType type){
		
		IncomingInfo incomingInfo = mock(IncomingInfo.class);
		when(incomingInfo.requiresSubsciption()).thenReturn(type != SubsciptionType.NONE);
		when(incomingInfo.getSubscriptionType()).thenReturn(type);

		return incomingInfo;
	}
}
