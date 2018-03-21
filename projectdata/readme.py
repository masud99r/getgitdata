from bs4 import BeautifulSoup
import urllib
import sys
reload(sys)
sys.setdefaultencoding('utf8')
def getreadme():
    fw = open("project_readme.txt", "a")
    print ("Failed to process, might be not available")
    with open("project_id_url.txt") as f:
        for line in f:
            line = line.replace("\n", "")
            line = line.replace("\r", "")
            id, url = line.split("\t")
            rmcontent, errcode = readmecontent(url)
            fw.write(str(id) + "\t" + str(url) + "\t" + str(rmcontent) + "\n")
            if errcode == -1:
                print url
    fw.close()
def readmecontent(htlm_url):
    readmetext = ""
    error_code = -1
    try:
        rawurl = htlm_url.replace("github.com", "raw.githubusercontent.com")
        rawurl += "/master/README.md"
        #print rawurl
        r = urllib.urlopen(rawurl).read()
        soup = BeautifulSoup(r)
        content = soup.text
        # do the processing
        content = content.replace("\r", " ")
        lines = str(content).split("\n")
        for line in lines:
            readmetext = readmetext + " " + line
        readmetext = readmetext.strip()
        error_code = 1
    except:
        error_code = -1
    if readmetext == "404: Not Found":
        readmetext = "NONE"
        error_code = -1
    return readmetext, error_code
if __name__ == '__main__':
    getreadme()
    #rm, ec = readmecontent("https://github.com/octokit/octokit.rb")
    #print rm
    #print ec