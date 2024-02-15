const puppeteer = require('puppeteer');
const fs = require('fs');

async function saveContent(url, outputPath) {
    	console.log("Page \"" + url + "\"");
	const browser = await puppeteer.launch();
	const page = await browser.newPage();

	try {
    
		await page.setViewport({width: 320, height: 600})
		await page.setUserAgent('Mozilla/5.0 (X11; Linux x86_64; rv:98.0) Gecko/20100101 Firefox/98.0')

		await page.goto(url, { waitUntil: 'networkidle0' });
		//await page.waitForSelector('body.blog');

		// Execute JavaScript on the page if needed
		// Example: Getting the page content after executing JavaScript
		const content = await page.evaluate(() => {
			return document.documentElement.outerHTML;
		});

		// Save the content to a file
		fs.writeFileSync(outputPath, content);
		console.log(`Content saved to ${outputPath}`);
	} catch (error) {
		console.error(`Error fetching content: ${error}`);
	} finally {
		await browser.close();
	}
}


async function scrap(start,end) {
	for (let i = start; i <= end; i++) {
	    console.log("Attempting to dl page " + i);
	    
	    page_url = "https://www.curseforge.com/minecraft/search?page=" + i + "&pageSize=50&sortBy=creation+date&class=mc-mods"
	    
	    output_path = "pages/s-" + i + ".html";
	    
	    await saveContent(page_url, output_path);
	    
	}
}

scrap(103,200);

// Example usage: node fetchContent.js https://example.com output.html
/*const url = process.argv[2];
const outputPath = process.argv[3];

if (!url || !outputPath) {
	console.error('Usage: node fetchContent.js <URL> <outputPath>');
} else {
	saveContent(url, outputPath);
}*/
