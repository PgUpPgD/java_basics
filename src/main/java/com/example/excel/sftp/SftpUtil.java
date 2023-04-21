package com.example.excel.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * @Description:
 * @Author: tangHC
 * @Date: 2023/3/16 11:48
 */
@Slf4j
@Component
public class SftpUtil {

    private static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);

    /**
     * FTPClient对象
     **/
    private static ChannelSftp ftpClient = null;
    /**
     *
     */
    private static Session sshSession = null;


    /**
     * 连接服务器
     *
     * @param host
     * @param port
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public static ChannelSftp getConnect(String host, String port, String userName, String password)
            throws Exception {
        try {
            log.info("获取ftp连接");
            JSch jsch = new JSch();
            // 获取sshSession
            sshSession = jsch.getSession(userName, host, Integer.parseInt(port));
            // 添加s密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            // 开启sshSession链接
            sshSession.connect();
            // 获取sftp通道
            ftpClient = (ChannelSftp) sshSession.openChannel("sftp");
            // 开启
            ftpClient.connect();
            logger.info("open channel success ..........");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("连接sftp服务器异常。。。。。。。。");
        }
        return ftpClient;
    }

    /**
     * 下载文件
     *
     * @param ftp_path    服务器文件路径
     * @param save_path   下载保存路径
     * @param oldFileName 服务器上文件名
     * @param newFileName 保存后新文件名
     */
    public static void download(String ftp_path, String save_path, String oldFileName,
                                String newFileName)
            throws Exception {
        FileOutputStream fos = null;
        try {
            ftpClient.cd(ftp_path);
            File file = new File(save_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String saveFile = save_path + newFileName;
            File file1 = new File(saveFile);
            fos = new FileOutputStream(file1);
            ftpClient.get(oldFileName, fos);
        } catch (Exception e) {
            logger.error("下载文件异常............", e.getMessage());
            throw new Exception("download file error............");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("close stream error..........");
                }
            }
        }
    }

    /**
     * sftp服务器
     * <b>将一个IO流解析，转化数组形式的集合<b>
     * csv文件解析
     *
     * @param in 文件inputStream流
     */
//    public static List<CustomerExcelVO> sftpCsvToCustomerVo(InputStream in,
//                                                            List<FieldDetail> fieldColuamns) {
//        Long startTime = System.currentTimeMillis();
//        log.info("解析文件 startTime {}", startTime);
//        List<CustomerExcelVO> tmpList = new ArrayList<>();
//        List<String[]> csvList = new ArrayList<>();
//        if (null != in) {
//            try {
//                InputStreamReader inputStream = new InputStreamReader(in, "GBK");
//                CSVParser csvParser = new CSVParserBuilder().build();
//                CSVReader reader = new CSVReaderBuilder(inputStream).withCSVParser(csvParser).build();
//                csvList = reader.readAll();
//                String[] title = csvList.get(0);
//                List<String> data = Arrays.asList(title);
//                //从第二列开始遍历
//                for (int i = 1; i < csvList.size(); i++) {
//                    String[] csv = csvList.get(i);
//                    JSONObject json = new JSONObject();
//                    for (int j = 0; j < fieldColuamns.size(); j++) {
//                        FieldDetail fieldDetail = fieldColuamns.get(j);
//                        if (data.contains(fieldDetail.getFileIdx())) {
//                            json.put(fieldDetail.geteField(), csv[data.lastIndexOf(fieldDetail.getFileIdx())]);
//                        }
//                    }
//                    CustomerExcelVO customerExcelVO = JSON.parseObject(JSON.toJSONString(json), CustomerExcelVO.class);
//                    tmpList.add(customerExcelVO);
//                }
//                Long endTime = System.currentTimeMillis();
//                log.info("解析文件11111共{}条 耗时 {}", csvList.size(), endTime - startTime);
//                return tmpList;
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (IOException | CsvException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    public static boolean isExistDir(String path, ChannelSftp sftp) {
        boolean isExist = false;
        try {
            sftp.lstat(path);
            isExist = true;
            return isExist;
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isExist = false;
            }
        }
        return isExist;

    }

    /**
     * sftp服务器
     * <b>将一个IO流解析，转化数组形式的集合<b>
     * csv文件解析
     *
     * @param in 文件inputStream流
     */
    public static List<String[]> sftpCsvToList(InputStream in) {
        Long startTime = System.currentTimeMillis();
        log.info("解析文件 startTime {}", startTime);
        if (null != in) {
            try {
                List<String[]> list = new ArrayList<>();
                InputStreamReader inputStream = new InputStreamReader(in, "GBK");
                String separator = new String(new char[]{0x1d});
                CSVParser csvParser = new CSVParserBuilder().withSeparator(separator.charAt(0)).build();
                CSVReader reader = new CSVReaderBuilder(inputStream).withCSVParser(csvParser).build();
                Iterator<String[]> iterator = reader.iterator();
                while (iterator.hasNext()) {
                    String[] next = iterator.next();
                    //去除第一行的表头，从第二行开始
                    //if (i >= 1) {}
                    list.add(next);
                }
                Long endTime = System.currentTimeMillis();
                log.info("解析文件22222 共{}条 耗时 {}", list.size(), endTime - startTime);
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 上传
     *
     * @param upload_path 上传文件路径
     * @param ftp_path    服务器保存路径
     * @param newFileName 新文件名
     */
    public static void uploadFile(String upload_path, String ftp_path, String newFileName)
            throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(upload_path));
            ftpClient.put(fis, newFileName);
            logger.info("{} file upload success..........", newFileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Upload file error.");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new Exception("close stream error.");
                }
            }
        }
    }

    /**
     * 关闭
     */
    public static void close() {
        logger.debug("close............");
        try {
            if (ftpClient.isClosed()) {
                ftpClient.disconnect();
            }
            if (sshSession.isConnected()) {
                sshSession.disconnect();
            }
        } catch (Exception e) {
            throw new RuntimeException("close stream error {}", e);
        }
    }

    public static InputStream readTarFile(InputStream in, String fileName) throws Exception {
        ArchiveInputStream archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream("tar", new GZIPInputStream(in));
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry) archiveInputStream.getNextEntry()) != null) {
            if (entry.getSize() > 0) {
                if (fileName.equals(entry.getName())) {
                    return archiveInputStream;
                }
            }
        }
        return archiveInputStream;
    }

    public static void main(String[] args) {
        try {
            ///wdzj/flie/customer20220624.csv
//            ChannelSftp sftp = sftpConnection("192.168.30.97",22,"root","Rzj@2017");
//            ChannelSftp sftp = getConnect("192.168.30.111", "22", "root", "Rzj@2017");
//            String path = "/wdzj/file/CBOD/_tar/SJXF067_CBOD_SAACNACN_20220720.tar.gz";

//            String path = "/tmp/ptar/abc.tar.gz";
//            sftp.cd("/tmp/");//进入所在路径
//            InputStream in = sftp.get(path);
//            getOkStatus(in);

            File file = new File("D:/a/b/c/a.txt");
            boolean b = file.renameTo(new File("D:/a/b/c/b.txt"));
            System.out.println(b);
//            FileUtils.forceDelete(file);
//            tarDir(file, "D:/a/adc.tar");

//            close();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void tarDir(File parentFile, String resultFilePath) throws IOException {
        FileOutputStream fos = null;
        TarArchiveOutputStream taos = null;
        try {
            fos = new FileOutputStream(resultFilePath);
            taos = new TarArchiveOutputStream(fos);
            for (File file : Objects.requireNonNull(parentFile.listFiles())){
                BufferedInputStream bis = null;
                FileInputStream fis = null;
                try {
                    TarArchiveEntry tae = new TarArchiveEntry(file);
                    //需指明 每个压缩文件的名字，以便于解压 TarArchiveEntry.getName(); 获取名字
                    tae.setName(new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
                    taos.putArchiveEntry(tae);
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    byte[] buffer = new byte[1024];
                    int i = bis.read(buffer, 0, 1024);
                    while (i != -1){
                        taos.write(buffer, 0, i);
                        i = bis.read(buffer, 0, 1024);
                    }
                }finally {
                    taos.closeArchiveEntry();
                    if (bis != null){
                        bis.close();
                    }
                    if (fis != null){
                        fis.close();
                    }
                }
            }
        } finally {
            if (taos != null)
                taos.close();
            if (fos != null)
                fos.close();
        }

    }

    public static void getOkStatus(InputStream in) throws IOException {
        log.info("判断.OK文件是否存在开始!");
        List<String> list = new ArrayList<>();
        list.add("123.pdf");
        list.add("456.pdf");
        list.add("789.pdf");
        ArchiveInputStream archiveInputStream = null;
        try {
            archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream("tar", new GZIPInputStream(in));
            TarArchiveEntry entry = null;
            while (archiveInputStream != null && (entry = (TarArchiveEntry) archiveInputStream.getNextEntry()) != null) {
                if (entry.getSize() > 0) {
                    String names = entry.getName();
                    for (String name : list) {
                        if (names.endsWith(name)) {
                            BufferedInputStream bis = new BufferedInputStream(archiveInputStream);
                            File file = new File("D:/a" + File.separator + name);
                            System.out.println("[" + File.separator + "]");
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedOutputStream bos = new BufferedOutputStream(fos);
                            byte[] buffer = new byte[1024];
                            int i = bis.read(buffer);
                            while (i != -1) {
                                bos.write(buffer, 0, i);
                                i = bis.read(buffer);
                            }
                            bos.flush();
                        }
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            in.close();
        }
    }

}
