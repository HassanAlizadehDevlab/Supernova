package com.android.data.repository.datasource.word

import com.android.data.entity.dao.WordDao
import com.android.data.entity.model.local.WordEntity
import com.android.data.entity.model.remote.Word
import com.android.data.extension.onError
import com.android.data.network.DataServiceWords
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * The [SmartWordDataSource] is responsible to implement and work with Data Sources.
 */
class SmartWordDataSource @Inject constructor(
    private val service: DataServiceWords,
    private val wordDao: WordDao
) : WordDataSource {

    override fun getWords(): Single<List<Word>> = service.words().onError()

    override fun checkWordsAvailability(): Completable {
        return wordDao.count()
            .flatMapCompletable {
                if (it > 0) Completable.complete()
                else Completable.error(IllegalStateException())
            }
    }

    override fun insertWords(words: List<WordEntity>): Completable =
        Completable.fromCallable { wordDao.insert(words) }

    override fun loadWordsByRange(range: Int): Single<List<WordEntity>> =
        wordDao.selectByRange(range)

    override fun passedWordsAvailability(): Completable = wordDao.firstPassedWord().ignoreElement()

    override fun deleteAll(): Completable = Completable.fromCallable { wordDao.deleteAll() }

    override fun updateWords(words: List<WordEntity>): Completable =
        Completable.fromCallable { wordDao.update(words) }

}