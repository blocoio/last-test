package io.bloco.lasttest.ui.common.lists;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter<T, IV extends ItemView<T>>
    extends RecyclerView.Adapter<RecyclerViewHolder<T, IV>> {

  private final Class<IV> itemClass;
  private final ItemClickListener<T> clickListener;
  private List<T> adapterList;

  public RecyclerAdapter(Class<IV> itemViewClass) {
    this(itemViewClass, new ArrayList<T>(), null);
  }

  public RecyclerAdapter(Class<IV> itemViewClass, @Nullable ItemClickListener<T> clickListener) {
    this(itemViewClass, new ArrayList<T>(), clickListener);
  }

  public RecyclerAdapter(Class<IV> itemViewClass, List<T> list) {
    this(itemViewClass, list, null);
  }

  public RecyclerAdapter(Class<IV> itemViewClass, List<T> list,
      @Nullable ItemClickListener<T> clickListener) {
    this.itemClass = itemViewClass;
    this.adapterList = list;
    this.clickListener = clickListener;
  }

  @Override public RecyclerViewHolder<T, IV> onCreateViewHolder(ViewGroup parent, int viewType) {
    IV itemView;
    try {
      itemView = itemClass.getConstructor(Context.class).newInstance(parent.getContext());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    final RecyclerViewHolder<T, IV> viewHolder = new RecyclerViewHolder<>(itemView);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (clickListener != null) {
          clickListener.onItemClick(adapterList.get(viewHolder.getAdapterPosition()));
        }
      }
    });

    return viewHolder;
  }

  @Override public void onBindViewHolder(RecyclerViewHolder<T, IV> holder, int position) {
    holder.bind(getItem(position));
  }

  @Override public int getItemCount() {
    return adapterList.size();
  }

  public void updateItems(final List<T> newItems) {
    RecyclerAdapterDiffUtilCallback callback = new RecyclerAdapterDiffUtilCallback(newItems);
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
    adapterList = new ArrayList<>(newItems);
    diffResult.dispatchUpdatesTo(this);
  }

  protected T getItem(int position) {
    return adapterList.get(position);
  }

  private class RecyclerAdapterDiffUtilCallback extends DiffUtil.Callback {

    private List<T> newItems;

    private RecyclerAdapterDiffUtilCallback(List<T> newItems) {
      this.newItems = newItems;
    }

    @Override public int getOldListSize() {
      return adapterList.size();
    }

    @Override public int getNewListSize() {
      return newItems.size();
    }

    @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
      return adapterList.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }

    @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
      return areItemsTheSame(oldItemPosition, newItemPosition);
    }
  }
}