package contacts.ru;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchCallback extends ItemTouchHelper.Callback {
 private ContactAdapter adapter;
 public  ItemTouchCallback(ContactAdapter adapter){
     this.adapter=adapter;
 }



    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {// Флаги для действий
        int swiperFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(0,swiperFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {//Удаление
    adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
