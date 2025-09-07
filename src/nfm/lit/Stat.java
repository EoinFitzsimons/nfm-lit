package nfm.lit;
/**
 * contrary to popular belief, this class was not jacked from src, but developed alongside it, for different, yet resemblant, cases.
 *
 * @author eli
 */
public class Stat {

    /**
     * the stats
     */

    public float acelf[] = new float[3];

    public int airc = 0;

    public float airs = 0F;

    public float bounce = 0F;

    public int clrad = 0;

    public float comprad = 0F;

    public float dammult = 0F;

    public int flipy = 0;

    public float grip = 0F;

    public int handb = 0;

    public int lift = 0;

    public int maxmag = 0;

    public float moment = 0;

    public int msquash = 0;

    public int powerloss = 0;

    public int push = 0;

    public int revlift = 0;

    public int revpush = 0;

    public float simag = 0F;

    public int swits[] = new int[3];

    public int turn = 0;

    public float dishandle = 0F;

    public float outdam = 0F;

    public int engine = 0;

    /**
     * set up a new stat
     *
     * @param car the car
     */
    public Stat(final int car) {
        acelf = EnhancedStatList.ENHANCED_ACELF[car].clone();
        swits = EnhancedStatList.ENHANCED_SWITS[car].clone();
        airc = EnhancedStatList.ENHANCED_AIRC[car];
        airs = EnhancedStatList.ENHANCED_AIRS[car];
        bounce = EnhancedStatList.ENHANCED_BOUNCE[car];
        clrad = EnhancedStatList.ENHANCED_CLRAD[car];
        comprad = EnhancedStatList.ENHANCED_COMPRAD[car];
        dammult = EnhancedStatList.ENHANCED_DAMMULT[car];
        flipy = EnhancedStatList.ENHANCED_FLIPY[car];
        grip = EnhancedStatList.ENHANCED_GRIP[car];
        handb = EnhancedStatList.ENHANCED_HANDB[car];
        lift = EnhancedStatList.ENHANCED_LIFT[car];
        maxmag = EnhancedStatList.ENHANCED_MAXMAG[car];
        moment = EnhancedStatList.ENHANCED_MOMENT[car];
        msquash = StatList.msquash[car];
        powerloss = StatList.powerloss[car];
        push = StatList.push[car];
        revlift = StatList.revlift[car];
        revpush = StatList.revpush[car];
        turn = StatList.turn[car];
        simag = StatList.simag[car];
        outdam = StatList.outdam[car];
        dishandle = StatList.dishandle[car];
        engine = StatList.engine[car];
    }

    public Stat(){
    }

}
