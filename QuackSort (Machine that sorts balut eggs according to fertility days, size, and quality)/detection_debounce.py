class DetectionDebouncer:
    def __init__(self, on_frames=4, off_frames=5, on_thr=0.6, off_thr=0.5):
        self.on_frames = on_frames   # how many frames needed to turn ON
        self.off_frames = off_frames # how many frames to turn OFF
        self.on_thr = on_thr         # confidence to turn ON
        self.off_thr = off_thr       # confidence to stay ON
        self.present = False
        self.on_cnt = 0
        self.off_cnt = 0    
   
    
    def update(self, conf):
        """
        Update debouncer state with a new confidence score.
        Returns True if detection considered 'active'.
        """
        if self.present:  # already ON
            if conf >= self.off_thr:
                self.off_cnt = 0
            else:
                self.off_cnt += 1
                if self.off_cnt >= self.off_frames:
                    self.present = False
                    self.on_cnt = 0
        else:  # currently OFF
            if conf >= self.on_thr:
                self.on_cnt += 1
                if self.on_cnt >= self.on_frames:
                    self.present = True
                    self.off_cnt = 0
            else:
                self.on_cnt = 0
        return self.present
    
    