/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.malban.vide.vedi.sound.ibxm;

import de.malban.config.Configuration;
import de.malban.gui.panels.LogPanel;
import static de.malban.gui.panels.LogPanel.WARN;
import de.malban.sound.tinysound.Stream;
import de.malban.sound.tinysound.TinySound;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *https://code.google.com/archive/p/micromod/downloads
 * @author malban
 */
public class IBXMPlayerJPanel extends javax.swing.JPanel {

    LogPanel log = (LogPanel) Configuration.getConfiguration().getDebugEntity();
    private static final int SAMPLE_RATE = 44100;
    private Module module;
    private IBXM ibxm;
    private volatile boolean playing;
    private int interpolation, sliderPos, samplePos, duration;
    private Thread playThread;
    private Timer updateTimer;
    
    /**
     * Creates new form IBXMPlayerJPanel
     */
    public IBXMPlayerJPanel() {
        initComponents();
        jPanel1.setVisible(false);
        updateTimer = new Timer( 200, new ActionListener() {
                public void actionPerformed( ActionEvent actionEvent ) {
                        if( !seekSlider.getValueIsAdjusting() ) {
                                if( seekSlider.getValue() != sliderPos )
                                        seek( seekSlider.getValue() );
                                sliderPos = samplePos;
                                if( sliderPos > duration ) sliderPos = duration;
                                seekSlider.setValue( sliderPos );
                        }
                        int secs = sliderPos / SAMPLE_RATE;
                        int mins = secs / 60;
                        secs = secs % 60;
                        timeLabel.setText( mins + ( secs < 10 ? ":0" : ":" ) + secs );
                }
        } );
    }
    public boolean setModfile(String filename)
    {
        try
        {
            loadModule( new File(filename));
        }
        catch (Throwable e)
        {
            log.addLog("Modfile: \""+filename+"\" not loaded");
            return false;
        }
        return true;
    }
    
    public void setVoicePlay(boolean [] voicePlay)
    {
        boolean wasPlaying = playing;
        if( playing ) stop();
        
        if (ibxm == null) return;
        ibxm.setVoicePlay(voicePlay);
        
        if (wasPlaying) play();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        instrumentList = new javax.swing.JList();
        seekSlider = new javax.swing.JSlider();
        playButton = new javax.swing.JButton();
        songLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Instruments"));

        instrumentList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(instrumentList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
        );

        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/malban/vide/images/control_play_blue.png"))); // NOI18N
        playButton.setToolTipText("Play current sample!");
        playButton.setMargin(new java.awt.Insets(0, 1, 0, -1));
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        songLabel.setText("No song loaded");

        timeLabel.setText("00:00");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("interpolation"));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("none");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("linear");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("sinc");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton3)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(timeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(seekSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(songLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(songLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(seekSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(playButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        if( playing ) stop(); else play();
    }//GEN-LAST:event_playButtonActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        setInterpolation( Channel.NEAREST );
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        setInterpolation( Channel.LINEAR );
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        setInterpolation( Channel.SINC );
    }//GEN-LAST:event_jRadioButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JList instrumentList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton playButton;
    private javax.swing.JSlider seekSlider;
    private javax.swing.JLabel songLabel;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables


	private synchronized void loadModule( File modFile ) throws IOException {		
		byte[] moduleData = new byte[ ( int ) modFile.length() ];
		FileInputStream inputStream = new FileInputStream( modFile );
		int offset = 0;
		while( offset < moduleData.length ) {
			int len = inputStream.read( moduleData, offset, moduleData.length - offset );
			if( len < 0 ) throw new IOException( "Unexpected end of file." );
			offset += len;
		}
		inputStream.close();
		module = new Module( moduleData );
		ibxm = new IBXM( module, SAMPLE_RATE );
		ibxm.setInterpolation( interpolation );
		duration = ibxm.calculateSongDuration();
		samplePos = sliderPos = 0;
		seekSlider.setMinimum( 0 );
		seekSlider.setMaximum( duration );
		seekSlider.setValue( 0 );
		songLabel.setText( module.songName.trim() );
		Vector<String> vector = new Vector<String>();
		Instrument[] instruments = module.instruments;
		for( int idx = 0, len = instruments.length; idx < len; idx++ ) {
			String name = instruments[ idx ].name;
			if( name.trim().length() > 0 )
				vector.add( String.format( "%03d: %s", idx, name ) );
		}
		instrumentList.setListData( vector );
                instr = vector;
	}
        Vector<String> instr;
        public Vector<String> getInstruments()
        {
            return instr;
        }

        // if more than one sample for instrument -> return sample 0
        // if instrument not available -> return null
        public Sample getInstrumentSample(int instrumentNo)
        {
            if (ibxm.module.instruments.length<instrumentNo) return null;
            if (ibxm.module.instruments[instrumentNo].samples.length<=0) return null;
            return ibxm.module.instruments[instrumentNo].samples[0];
        }
        
	private synchronized void playOrg() {
		if( ibxm != null ) {
			playing = true;
			playThread = new Thread( new Runnable() {
				public void run() {
					int[] mixBuf = new int[ ibxm.getMixBufferLength() ];
					byte[] outBuf = new byte[ mixBuf.length * 4 ];
					AudioFormat audioFormat = null;
					SourceDataLine audioLine = null;
					try {
						audioFormat = new AudioFormat( SAMPLE_RATE, 16, 2, true, true );
						audioLine = AudioSystem.getSourceDataLine( audioFormat );
						audioLine.open();
						audioLine.start();
						while( playing ) {
							int count = getAudio( mixBuf );
							int outIdx = 0;
							for( int mixIdx = 0, mixEnd = count * 2; mixIdx < mixEnd; mixIdx++ ) {
								int ampl = mixBuf[ mixIdx ];
								if( ampl > 32767 ) ampl = 32767;
								if( ampl < -32768 ) ampl = -32768;
								outBuf[ outIdx++ ] = ( byte ) ( ampl >> 8 );
								outBuf[ outIdx++ ] = ( byte ) ampl;
							}
							audioLine.write( outBuf, 0, outIdx );
						}
						audioLine.drain();
					} catch( Exception e ) {
						JOptionPane.showMessageDialog( Configuration.getConfiguration().getMainFrame(),
							e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
					} finally {
						if( audioLine != null && audioLine.isOpen() ) audioLine.close();
					}	
				}
			} );
			playThread.start();
			updateTimer.start();
                        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/malban/vide/images/control_stop_blue.png"))); // NOI18N
		}
	}
	private synchronized void play() 
        {
            if( ibxm != null ) 
            {
                playing = true;
                playThread = new Thread( new Runnable() 
                {
                    public void run() 
                    {
                        int[] mixBuf = new int[ ibxm.getMixBufferLength() ];
                        byte[] outBuf = new byte[ mixBuf.length * 4 ];
                        int count;
                        int outIdx;
                        int mixIdx;
                        int mixEnd;
                        Stream audioLine = null;
                        try 
                        {
                            audioLine = TinySound.getOutStreamMod();       
                            audioLine.start();
                            while( playing ) 
                            {
                                // generates 16 bit signed audio two channels big endian
                                count = getAudio( mixBuf );
                                outIdx = 0;
                                for(mixIdx = 0, mixEnd = count * 2; mixIdx < mixEnd; mixIdx++ ) 
                                {
                                    int ampl = mixBuf[ mixIdx ];
                                    if( ampl > 32767 ) ampl = 32767;
                                    if( ampl < -32768 ) ampl = -32768;
                                    outBuf[ outIdx++ ] = ( byte ) ( ampl >> 8 );
                                    outBuf[ outIdx++ ] = ( byte ) ampl;
                                }
                                // wait till data can be put to line, since
                                // "our" sourceline does not block
                                while (audioLine.available()<outIdx) Thread.sleep(5);
                                audioLine.write( outBuf, 0, outIdx );
                            }
                            audioLine.stop();
                            audioLine.unload();
                            audioLine = null;
                        } 
                        catch( Exception e ) 
                        {
                            log.addLog(e, WARN);
                            if (audioLine != null) 
                            {
                                audioLine.stop();
                                audioLine.unload();
                            }
                        } 
                    }
                }, "IBXM Modplayer");
                playThread.start();
                updateTimer.start();
                playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/malban/vide/images/control_stop_blue.png"))); // NOI18N
            }
	}
	public synchronized void stop() {
		playing = false;
		try {
			if( playThread != null ) playThread.join();
		} catch( InterruptedException e ) {
		}
		updateTimer.stop();
                playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/malban/vide/images/control_play_blue.png"))); // NOI18N
	}

	private synchronized void seek( int pos ) {
		samplePos = ibxm.seek( pos );
	}

	private synchronized void setInterpolation( int interpolation ) {
		this.interpolation = interpolation;
		if( ibxm != null ) ibxm.setInterpolation( interpolation );
	}

	private synchronized int getAudio( int[] mixBuf ) {
		int count = ibxm.getAudio( mixBuf );
		samplePos += count;
		return count;
	}

	private synchronized void saveWav( File wavFile, int time, boolean fade ) throws IOException {
		stop();
		seek( 0 );
		WavInputStream wavInputStream = new WavInputStream( ibxm, time, fade );
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream( wavFile );
			byte[] buf = new byte[ ibxm.getMixBufferLength() * 4 ];
			int remain = wavInputStream.getBytesRemaining();
			while( remain > 0 ) {
				int count = remain > buf.length ? buf.length : remain;
				count = wavInputStream.read( buf, 0, count );
				fileOutputStream.write( buf, 0, count );
				remain -= count;
			}
		} finally {
			if( fileOutputStream != null ) fileOutputStream.close();
			seek( 0 );
		}
	}

}
