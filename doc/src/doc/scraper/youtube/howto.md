# Get Stream URL from Youtube

## Parse Video ID from URL

The Video at `https://www.youtube.com/watch?v=S6P3ZLOUgXk` has the handle `S6P3ZLOUgXk`
 
## Get Metadata for Video ID

Metadata can be retrieved with a get request to  
`http://gdata.youtube.com/feeds/api/videos/<videoid>` 

The response will look like this:

```xml
	<?xml version='1.0' encoding='UTF-8'?>
	<entry xmlns='http://www.w3.org/2005/Atom' xmlns:gd='http://schemas.google.com/g/2005'
	xmlns:yt='http://gdata.youtube.com/schemas/2007' xmlns:media='http://search.yahoo.com/mrss/'>
	<handle>http://gdata.youtube.com/feeds/api/videos/S6P3ZLOUgXk</handle>
	<published>2013-06-25T15:48:00.000Z</published>
	<updated>2015-01-24T09:36:21.000Z</updated>
	<category scheme='http://schemas.google.com/g/2005#kind' term='http://gdata.youtube.com/schemas/2007#video' />
	<category scheme='http://gdata.youtube.com/schemas/2007/categories.cat'
		term='Animals' label='Pets &amp; Animals' />
	<title type='text'>Dschungelfieber - Reise zu den Monstern (Doku)
	</title>
	<content type='text'>Der Dschungel im Amazonasbecken ist der größte
		zusammenhängende Regenwald der Welt. Lediglich ein Viertel des mehrere
		Millionen Quadratkilometer großen Waldgebietes gilt als erforscht.
		Doch auch die bekannten Gebiete bergen noch viele Geheimnisse. Jaguar,
		Ozelot und Faultier sind die populärsten Bewohner des
		Amazonas-Regenwaldes. In einzigartigen Bildern porträtiert der zweite
		Teil den Regenwald und seine eigentümlichsten tierischen und
		pflanzlichen Bewohner.

		Doku | 2005
	</content>
	<link rel='alternate' type='text/html'
		href='http://www.youtube.com/watch?v=S6P3ZLOUgXk&amp;feature=youtube_gdata' />
	<link rel='http://gdata.youtube.com/schemas/2007#video.related'
		type='application/atom+xml' href='http://gdata.youtube.com/feeds/api/videos/S6P3ZLOUgXk/related' />
	<link rel='http://gdata.youtube.com/schemas/2007#mobile' type='text/html'
		href='http://m.youtube.com/details?v=S6P3ZLOUgXk' />
	<link rel='self' type='application/atom+xml'
		href='http://gdata.youtube.com/feeds/api/videos/S6P3ZLOUgXk' />
	<author>
		<name>IdNDokus</name>
		<uri>http://gdata.youtube.com/feeds/api/users/IdNDokus</uri>
	</author>
	<gd:comments>
		<gd:feedLink rel='http://gdata.youtube.com/schemas/2007#comments'
			href='http://gdata.youtube.com/feeds/api/videos/S6P3ZLOUgXk/comments'
			countHint='164' />
	</gd:comments>
	<yt:hd />
	<media:group>
		<media:category label='Pets &amp; Animals'
			scheme='http://gdata.youtube.com/schemas/2007/categories.cat'>Animals</media:category>
		<media:content
			url='http://www.youtube.com/v/S6P3ZLOUgXk?version=3&amp;f=videos&amp;app=youtube_gdata'
			type='application/x-shockwave-flash' medium='video' isDefault='true'
			expression='full' duration='2562' yt:format='5' />
		<media:content
			url='rtsp://r4---sn-4g57kuee.c.youtube.com/CiILENy73wIaGQl5gZSzZPejSxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp'
			type='video/3gpp' medium='video' expression='full' duration='2562'
			yt:format='1' />
		<media:content
			url='rtsp://r4---sn-4g57kuee.c.youtube.com/CiILENy73wIaGQl5gZSzZPejSxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp'
			type='video/3gpp' medium='video' expression='full' duration='2562'
			yt:format='6' />
		<media:description type='plain'>Der Dschungel im
			Amazonasbecken ist der größte zusammenhängende Regenwald der Welt.
			Lediglich ein Viertel des mehrere Millionen Quadratkilometer großen
			Waldgebietes gilt als erforscht. Doch auch die bekannten Gebiete
			bergen noch viele Geheimnisse. Jaguar, Ozelot und Faultier sind die
			populärsten Bewohner des Amazonas-Regenwaldes. In einzigartigen
			Bildern porträtiert der zweite Teil den Regenwald und seine
			eigentümlichsten tierischen und pflanzlichen Bewohner.

			Doku | 2005
		</media:description>
		<media:keywords />
		<media:player
			url='http://www.youtube.com/watch?v=S6P3ZLOUgXk&amp;feature=youtube_gdata_player' />
		<media:thumbnail url='http://i.ytimg.com/vi/S6P3ZLOUgXk/0.jpg'
			height='360' width='480' time='00:21:21' />
		<media:thumbnail url='http://i.ytimg.com/vi/S6P3ZLOUgXk/1.jpg'
			height='90' width='120' time='00:10:40.500' />
		<media:thumbnail url='http://i.ytimg.com/vi/S6P3ZLOUgXk/2.jpg'
			height='90' width='120' time='00:21:21' />
		<media:thumbnail url='http://i.ytimg.com/vi/S6P3ZLOUgXk/3.jpg'
			height='90' width='120' time='00:32:01.500' />
		<media:title type='plain'>Dschungelfieber - Reise zu den
			Monstern (Doku)</media:title>
		<yt:duration seconds='2562' />
	</media:group>
	<gd:rating average='4.6084657' max='5' min='1' numRaters='378'
		rel='http://schemas.google.com/g/2005#overall' />
	<yt:statistics favoriteCount='0' viewCount='249940' />
    </entry>
```