import re
from click import File
import plyj.parser as plyj
import zipfile
from io import BytesIO

src = []


def read_zip_file():

    with zipfile.ZipFile('Open Source OO project-20230329.zip', mode='r') as srczip:
        for projectName in srczip.namelist():
            if re.search(r'\.zip$', projectName) is not None:
                # We have a zip within a zip
                projectFile = BytesIO(srczip.read(projectName))
                with zipfile.ZipFile(projectFile) as project:
                    for fileName in project.namelist():
                        # Now we can extract
                        if (fileName.endswith('.java')) & ('src' in fileName) & (not 'test' in fileName):
                            file = File(project.open(fileName))
                            src.append([fileName, file])


def parser():
    parser = plyj.Parser()
    tree = parser.parse_file(src[0][1])
    print(tree)


read_zip_file()
# parser()
