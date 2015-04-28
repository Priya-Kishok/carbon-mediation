package org.wso2.carbon.mediation.initializer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by priyakishok on 4/27/15.
 */
public class CreateApplicationZip {

    List<String> fileList;
    List<String> directoryList;
    //  private static final String OUTPUT_ZIP_FILE = "/home/priyakishok/Training-Project/cApp/test1/wso2esb-4.9.0-M8-SNAPSHOT/repository/deployment/server/carbonapps/";
    //   private static final String SOURCE_FOLDER = "/home/priyakishok/Training-Project/cApp/test1/wso2esb-4.9.0-M8-SNAPSHOT/cApp_faulty_jms_1.0.0.car";

    private static String SOURCE_FOLDER;
    private static String OUTPUT_ZIP_FILE;

   public CreateApplicationZip(){
        fileList = new ArrayList<String>();
        directoryList = new ArrayList<String>();
    }

    public void createZip(String sourcePath,String destPath)
    {
        CreateApplicationZip appZip = new CreateApplicationZip();
        SOURCE_FOLDER = sourcePath;
        OUTPUT_ZIP_FILE = destPath;
        System.out.println(" File Path :::: "+sourcePath);
        System.out.println(" Out Path :::: "+destPath);

        appZip.generateFileList(new File(SOURCE_FOLDER));
        appZip.zipIt(OUTPUT_ZIP_FILE);
    }

    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile){

        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);

            for(String file : this.directoryList){

                //System.out.println("File Added : " + file);
                ZipEntry ze= new ZipEntry(file);
                zos.putNextEntry(ze);
            }

            for(String file : this.fileList){

                //System.out.println("File Added : " + file);
                ZipEntry ze= new ZipEntry(file);
                zos.putNextEntry(ze);

                FileInputStream in =
                        new FileInputStream(SOURCE_FOLDER + File.separator + file);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }
            zos.closeEntry();
            //remember close it
            zos.close();

            System.out.println("Done");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     */
    public void generateFileList(File node){

        //add file only
        if(node.isFile()){
            fileList.add(generateZipEntry(node.getAbsolutePath().toString()));
        }

        if(node.isDirectory()){
            String[] subNote = node.list();
            if(!node.getName().contains("cApp_faulty_jms_1.0.0"))
                directoryList.add(generateZipEntry(node.getAbsolutePath().toString()+"/"));
            for(String filename : subNote){
                generateFileList(new File(node, filename));
            }
        }

    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
        return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }


}

