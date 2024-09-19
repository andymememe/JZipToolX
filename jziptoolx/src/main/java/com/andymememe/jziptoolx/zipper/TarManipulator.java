/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andymememe.jziptoolx.zipper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarInputStream;

import com.andymememe.jziptoolx.treer.TreeManipulator;

/**
 *
 * @author andymememe
 */
public class TarManipulator extends ComManipulator {

    private File _file;

    public TarManipulator(TreeManipulator treeMan) {
        super(treeMan);
    }

    @Override
    public boolean openCom(File file) {
        boolean result = true;
        TarInputStream tarInputStream;
        _file = file;
        TarEntry entry;
        _treeManipulator.generateRoot(_file.getName());
        try {
            tarInputStream = new TarInputStream(new BufferedInputStream(new FileInputStream(_file)));
            while ((entry = tarInputStream.getNextEntry()) != null) {
                /* Fix Chinese Error */
                char[] intFilename = entry.getName().toCharArray();
                byte[] byteFilename = new byte[intFilename.length];
                int i = 0;
                for (int aChar : intFilename) {
                    aChar = aChar & 0xFF;
                    byteFilename[i++] = (byte) aChar;
                }
                String filename = new String(byteFilename);
                String[] structure = filename.split("[\\/]");

                _treeManipulator.addEntryNode(structure);
            }
            tarInputStream.close();
        } catch (IOException ex) {
            result = false;
            Logger.getLogger(TarManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean extractCom(String dir) {
        boolean result = true;
        int count;
        byte[] data = new byte[2048];
        TarInputStream tarInputStream;
        BufferedOutputStream bufferOutputStream;
        try {
            tarInputStream = new TarInputStream(new BufferedInputStream(new FileInputStream(_file)));
            TarEntry entry;
            while ((entry = tarInputStream.getNextEntry()) != null) {
                char[] intFilename = entry.getName().toCharArray();
                byte[] byteFilename = new byte[intFilename.length];
                int i = 0;
                /* Fix Chinese Error */
                for (int aChar : intFilename) {
                    aChar = aChar & 0xFF;
                    byteFilename[i++] = (byte) aChar;
                }
                String filename = new String(byteFilename);
                if (filename.startsWith("PaxHeader")) {}
                /* Entry is intFilename dirctory */
                else if (entry.isDirectory()) {
                    result = _doMkDir(dir, filename);
                } /* Entry is intFilename file */ else {
                    File newFile = new File(dir + File.separator + filename);
                    /* See if file exist or not */
                    if (!newFile.exists()) {
                        newFile.createNewFile();
                    } else {
                        int replaceResult = _alertWhenExit(dir + File.separator + filename);
                        newFile = _doReplaceJob(newFile, replaceResult);
                        if (newFile == null) {
                            tarInputStream.close();
                            return false;
                        }
                    }

                    bufferOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));

                    while ((count = tarInputStream.read(data)) != -1) {
                        bufferOutputStream.write(data, 0, count);
                    }

                    bufferOutputStream.flush();
                    bufferOutputStream.close();
                }
            }
            tarInputStream.close();
        } catch (IOException ex) {
            result = false;
            Logger.getLogger(TarManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
