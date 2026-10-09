package towerdefense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

    /**A class for the management of Projectile)
    
     */
    public class ManageProjectile{
        
        /**the projectile to be managed */
        private Projectile projectile;
        /**the tower to which the projectile is assigned */
        private Tower assignedTower;
        /**the board in which the projectile manager operates  */
        private Board b;
        
        /**
         * builds a new projectile manager 
         * @param projectile the projectile to be managed
         * @param t the tower to which the projectile is assigned
         * @param b the board in which the projectile manager operates
         */
        public ManageProjectile(Projectile projectile, Tower t, Board b){
            this.projectile = projectile;
            this.assignedTower = t;
            this.b = b;
        
        }

// ==================== METHOD GET ====================

    /**
     * provides the range of cells that a Bomb or ExtraBomb will hit
     * @param impactCenter the center of the impact, where the bomb hits
     * @return the list of cells in the range
     */
    public Map<Cell, Integer> getCellsInImpact(Cell impactCenter){
        Map<Cell, Integer> res = new HashMap<>();
        int cx = impactCenter.getX();
        int cy = impactCenter.getY();
        int r = this.projectile.getRange();
        int dfi = (r - (r % 100)) / 100;
        if(r % 100 > 0){
            dfi += 1;
        }
        for(int i = -dfi; i<= dfi; i++){
            for(int j = -dfi; j<= dfi; j++){
                int dx = cx + i;
                int dy = cy + j;
                if(dx >= 0 && dx < this.b.getBoard()[0].length && dy >= 0 && dy < this.b.getBoard().length){
                    if((i == -dfi || i == dfi || j == -dfi || j == dfi) && r % 100 > 0){
                        res.put(this.b.getCell(dx, dy), r % 100);
                    }
                    else{ 
                        res.put(this.b.getCell(dx, dy), 100);
                    }
                }
            }
        }

        return res;
    }

     /**
     * provides the list of Balloons that a Bomb or ExtraBomb will hit
     * @param cellList a map of the cell in range of the Bomb
     * @return the list of balloons in the range
     */
    public ArrayList<Balloon> getBalloonsInImpact(Map<Cell, Integer> cellList){
        ArrayList<Balloon> res = new ArrayList<>();
        for(Map.Entry<Cell, Integer> entry : cellList.entrySet()){
            ArrayList<Balloon> balloonsInCell = entry.getKey().getBalloonList();
            for(Balloon balloon : balloonsInCell){
                res.add(balloon);
            }
        }
        return res;
    }


// ==================== DAMAGE METHOD ====================

    /**
     * makes damage to the balloon on which it is shot, takes in account the case of a BombTower
     * @param target the balloon to be hurt
     */
    public void hurtBalloon(Balloon target){
        if(this.assignedTower.isBombTower()){

            for(Balloon hurtBalloon : getBalloonsInImpact(getCellsInImpact(this.b.getCell(target.getX(),target.getY())))){
                hurtBalloon.hurt(hurtBalloon.getResistance()-this.projectile.strength);
            }
        }
        else{
            this.assignedTower.shoot(target);
        }
        
    }
}
